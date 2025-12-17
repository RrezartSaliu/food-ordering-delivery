package org.example.order_service.Service;

import org.example.order_service.Domain.model.CartItem;
import org.example.order_service.Domain.model.ShoppingCart;

public interface CartItemService {
    CartItem createCartItem(ShoppingCart shoppingCart, Long itemId, Integer quantity);
}
