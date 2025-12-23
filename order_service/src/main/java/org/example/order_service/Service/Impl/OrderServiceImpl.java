package org.example.order_service.Service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.order_service.Domain.model.*;
import org.example.order_service.Repository.OrderRepository;
import org.example.order_service.Service.OrderService;
import org.example.order_service.Service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
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

        // Copy cart items to order items
        List<OrderItem> orderItems = cart.getItems().stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(null); // will set later
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

        // Link each orderItem to order
        orderItems.forEach(oi -> oi.setOrder(order));

        // Save order first
        orderRepository.save(order);

        // THEN clear the cart
        shoppingCartService.emptyShoppingCart(userId);

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
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Order delivered(Long id) {
        Order order = this.findById(id);
        order.setOrderStatus(OrderStatus.DELIVERED);
        return orderRepository.save(order);
    }
}
