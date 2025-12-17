package org.example.order_service.Service;

import org.example.order_service.Domain.model.ShoppingCart;


public interface ShoppingCartService {
    ShoppingCart createShoppingCart(Long userId);
    ShoppingCart getShoppingCart(Long userId);
    ShoppingCart addItem(Long userId, Long itemId, Integer quantity);
    ShoppingCart removeItem(Long userId, Long itemId);
}
