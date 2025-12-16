package org.example.order_service.Domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long menuItemId;
    private String name;
    private BigDecimal price;
    private int quantity;
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private ShoppingCart shoppingCart;
}
