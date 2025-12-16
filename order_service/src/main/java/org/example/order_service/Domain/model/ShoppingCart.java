package org.example.order_service.Domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ShoppingCart {
    @Id
    private Long userId;
    private List<Long> menuItemIds;
}
