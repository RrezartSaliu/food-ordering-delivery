package org.example.order_service.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.order_service.Domain.model.CartItem;
import org.example.order_service.Domain.model.ShoppingCart;
import org.example.order_service.Domain.model.Status;
import org.example.order_service.Repository.ShoppingCartRepository;
import org.example.order_service.Service.CartItemService;
import org.example.order_service.Service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemService cartItemService;

    @Override
    public ShoppingCart createShoppingCart(Long userId) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        shoppingCart.setStatus(Status.valueOf("ACTIVE"));

        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getShoppingCart(Long userId) {
        return shoppingCartRepository.findByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseGet(() ->{
                    ShoppingCart shoppingCart1 = new ShoppingCart();
                    shoppingCart1.setUserId(userId);
                    shoppingCart1.setStatus(Status.valueOf("ACTIVE"));
                    shoppingCart1.setItems(new ArrayList<>());
                    return shoppingCartRepository.save(shoppingCart1);
                });
    }

    @Override
    public ShoppingCart addItem(Long userId, Long itemId, Integer quantity) {
        ShoppingCart shoppingCart = getShoppingCart(userId);
        List<CartItem> items = shoppingCart.getItems();
        items.add(cartItemService.createCartItem(shoppingCart, itemId, quantity));
        shoppingCart.setItems(items);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart removeItem(Long userId, Long itemId) {
        ShoppingCart shoppingCart = getShoppingCart(userId);

        shoppingCart.getItems().removeIf(cartItem -> cartItem.getId().equals(itemId));

        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart emptyShoppingCart(Long userId) {
        ShoppingCart shoppingCart = getShoppingCart(userId);
        shoppingCart.getItems().clear();
        return shoppingCartRepository.save(shoppingCart);
    }
}
