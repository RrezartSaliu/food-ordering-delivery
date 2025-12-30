package org.example.order_service.Repository;

import jakarta.persistence.LockModeType;
import org.example.order_service.Domain.model.Order;
import org.example.order_service.Domain.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByUserIdOrderByOrderDateTimeDesc(Long userId);
    List<Order> findAllByRestaurantIdAndOrderStatusOrderByOrderDateTimeDesc(
            Long restaurantId,
            OrderStatus orderStatus
    );
    Optional<Order> findById(Long id);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM Order o WHERE o.id = :id")
    Optional<Order> findByIdForUpdate(@Param("id") Long id);
    List<Order> findAllByDriverIdOrderByOrderDateTimeDesc(Long driverId);
    List<Order> findAllByDriverIdAndOrderStatusOrderByOrderDateTimeDesc(Long driverId, OrderStatus orderStatus);
}
