package org.example.order_service.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.order_service.Domain.model.CartItem;
import org.example.order_service.Domain.model.ShoppingCart;
import org.example.order_service.Repository.CartItemRepository;
import org.example.order_service.Service.CartItemService;
import org.example.order_service.Service.MenuItemSnapshotService;
import org.example.order_service.Service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final MenuItemSnapshotService menuItemSnapshotService;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem createCartItem(ShoppingCart shoppingCart, Long itemId, Integer quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setMenuItemSnapshot(menuItemSnapshotService.getMenuItemSnapshot(itemId));
        cartItem.setQuantity(quantity);
        cartItem.setCart(shoppingCart);

        return cartItemRepository.save(cartItem);
    }
}
