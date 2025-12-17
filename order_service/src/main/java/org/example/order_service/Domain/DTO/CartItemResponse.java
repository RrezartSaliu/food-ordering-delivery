package org.example.order_service.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemResponse {
    private Long id;
    private MenuItemSnapshotResponse menuItemSnapshot;
    private Integer quantity;
}
