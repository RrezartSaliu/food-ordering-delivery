package org.example.order_service.Repository;

import org.example.order_service.Domain.model.Order;
import org.example.order_service.Domain.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByUserIdOrderByOrderDateTimeDesc(Long userId);
    List<Order> findAllByRestaurantIdAndOrderStatusOrderByOrderDateTimeDesc(
            Long restaurantId,
            OrderStatus orderStatus
    );
    List<Order> findAllByDriverIdOrderByOrderDateTimeDesc(Long driverId);
}
