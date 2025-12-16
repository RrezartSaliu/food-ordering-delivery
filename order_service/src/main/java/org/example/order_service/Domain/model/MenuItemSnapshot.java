package org.example.order_service.Domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class MenuItemSnapshot {
    @Id
    private Long id;

    private Long restaurantId;
    private String name;
    private BigDecimal price;
    private String category;
    private boolean active;
}
