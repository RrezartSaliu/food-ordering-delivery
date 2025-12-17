package org.example.order_service.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequest {
    private Long menuItemId;
    private Integer quantity;
}
