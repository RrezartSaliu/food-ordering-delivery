package org.example.order_service.Service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.OrderCreatedEvent;
import org.example.order_service.Config.RabbitConfig;
import org.example.order_service.Domain.model.*;
import org.example.order_service.Repository.OrderRepository;
import org.example.order_service.Service.OrderService;
import org.example.order_service.Service.ShoppingCartService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final RabbitTemplate rabbitTemplate;
    private final OrderRepository orderRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Order create(BigDecimal amount, Long userId) {
        ShoppingCart cart = shoppingCartService.getShoppingCart(userId);

        List<OrderItem> orderItems = cart.getItems().stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(null);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setMenuItemSnapshot(item.getMenuItemSnapshot());
            return orderItem;
        }).collect(Collectors.toList());

        Order order = new Order();
        order.setItems(orderItems);
        order.setUserId(userId);
        order.setAmount(amount);
        order.setOrderStatus(OrderStatus.WAITING);
        order.setOrderDateTime(LocalDateTime.now());
        order.setRestaurantId(order.getItems().stream().findFirst().get().getMenuItemSnapshot().getRestaurantId());

        orderItems.forEach(oi -> oi.setOrder(order));

        orderRepository.save(order);

        shoppingCartService.emptyShoppingCart(userId);

        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        orderCreatedEvent.setOrderId(order.getId());
        orderCreatedEvent.setRestaurantId(order.getItems().stream().findFirst().get().getMenuItemSnapshot().getRestaurantId());

        rabbitTemplate.convertAndSend(RabbitConfig.ORDER_QUEUE, orderCreatedEvent);

        return order;
    }


    @Override
    public Order deliver(Long id) {
        Order order = this.findById(id);
        order.setOrderStatus(OrderStatus.DELIVERING);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findAllByUserId(Long userId) {
        return orderRepository.findAllByUserIdOrderByOrderDateTimeDesc(userId);
    }

    @Override
    public Order delivered(Long id) {
        Order order = this.findById(id);
        order.setOrderStatus(OrderStatus.DELIVERED);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAllByRestaurantIdAndOrderStatus(Long restaurantId, OrderStatus orderStatus){
        return orderRepository.findAllByRestaurantIdAndOrderStatusOrderByOrderDateTimeDesc(restaurantId, orderStatus);
    }
}
