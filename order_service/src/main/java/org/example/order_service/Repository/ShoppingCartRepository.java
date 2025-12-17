package org.example.order_service.Repository;

import org.example.order_service.Domain.model.ShoppingCart;
import org.example.order_service.Domain.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserIdAndStatus(Long userId, Status status);
}
