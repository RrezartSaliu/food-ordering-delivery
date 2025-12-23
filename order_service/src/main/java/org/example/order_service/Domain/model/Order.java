package org.example.order_service.Domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> items;
    private LocalDateTime orderDateTime;
    private Long userId;
    private OrderStatus orderStatus;
}
