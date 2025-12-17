package org.example.order_service.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ShoppingCartResponse {
    private List<CartItemResponse> items;
}
