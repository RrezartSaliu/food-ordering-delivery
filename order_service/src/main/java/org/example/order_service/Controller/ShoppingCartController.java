package org.example.order_service.Controller;

import lombok.RequiredArgsConstructor;
import org.example.ApiResponse;
import org.example.order_service.Domain.DTO.CartItemRequest;
import org.example.order_service.Domain.DTO.ShoppingCartResponse;
import org.example.order_service.Domain.model.ShoppingCart;
import org.example.order_service.Mapper.ShoppingCartMapper;
import org.example.order_service.Service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shopping-cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartMapper shoppingCartMapper;

    @GetMapping("/get-shopping-cart")
    public ResponseEntity<ApiResponse<ShoppingCartResponse>> getMyShoppingCart(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String role) {
        if (role.equals("ROLE_USER")){
            ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(Long.valueOf(userId));
            ShoppingCartResponse shoppingCartResponse = shoppingCartMapper.toResponse(shoppingCart);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cart fetched successfully", shoppingCartResponse));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false, "Not customer user", null));
    }

    @PostMapping("/add-item")
    public ResponseEntity<ApiResponse<ShoppingCartResponse>> addItem(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String role, @RequestBody CartItemRequest cartItemRequest){
        if (role.equals("ROLE_USER")){
            shoppingCartService.getShoppingCart(Long.valueOf(userId));
            ShoppingCart shoppingCart = shoppingCartService.addItem(Long.valueOf(userId), cartItemRequest.getMenuItemId(), cartItemRequest.getQuantity());
            return ResponseEntity.ok(new ApiResponse<>(true, "Item Added", shoppingCartMapper.toResponse(shoppingCart)));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false, "Not customer user", null));
    }

    @DeleteMapping("/remove-item")
    public ResponseEntity<ApiResponse<ShoppingCartResponse>> removeItem(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String role, @RequestBody CartItemRequest cartItemRequest){
        if (role.equals("ROLE_USER")){
            ShoppingCart shoppingCart = shoppingCartService.removeItem(Long.valueOf(userId), cartItemRequest.getMenuItemId());

            return ResponseEntity.ok(new ApiResponse<>(true, "Item removed"+cartItemRequest.getMenuItemId(), shoppingCartMapper.toResponse(shoppingCart)));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false, "Not customer user", null));
    }
}
