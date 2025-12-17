package org.example.order_service.Domain.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "menu_item_snapshot_id", nullable = false)
    private MenuItemSnapshot menuItemSnapshot;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private ShoppingCart cart;
}
