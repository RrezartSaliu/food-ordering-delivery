package org.example.order_service.Service;

import org.example.order_service.Domain.model.CartItem;
import org.example.order_service.Domain.model.Order;
import org.example.order_service.Domain.model.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Order findById(Long id);
    Order create(BigDecimal amount, Long userId, Double longitude, Double latitude, String address);
    Order deliver(Long id, Long driverId);
    List<Order> findAll();
    List<Order> findAllByUserId(Long userId);
    Order delivered(Long id);
    List<Order> findAllByRestaurantIdAndOrderStatus(Long restaurantId, OrderStatus orderStatus);
    List<Order> findAllByDriverIdAndOrderStatus(Long driverId, OrderStatus orderStatus);
}
