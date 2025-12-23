package org.example.order_service.Controller;

import lombok.RequiredArgsConstructor;
import org.example.ApiResponse;
import org.example.PaymentEvent;
import org.example.order_service.Domain.DTO.CardInfoRequest;
import org.example.order_service.Domain.DTO.CartItemRequest;
import org.example.order_service.Domain.DTO.ShoppingCartResponse;
import org.example.order_service.Domain.model.ShoppingCart;
import org.example.order_service.Mapper.ShoppingCartMapper;
import org.example.order_service.Producer.KafkaProducer;
import org.example.order_service.Service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shopping-cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartMapper shoppingCartMapper;
    private final KafkaProducer kafkaProducer;

    @GetMapping("/get-shopping-cart")
    public ResponseEntity<ApiResponse<ShoppingCartResponse>> getMyShoppingCart(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String role) {
        if (role.equals("ROLE_USER")){
            ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(Long.valueOf(userId));
            ShoppingCartResponse shoppingCartResponse = shoppingCartMapper.toResponse(shoppingCart);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cart fetched successfully", shoppingCartResponse));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false, "Not customer user", null));
    }

    @GetMapping("/get-shopping-cart-count")
    public ResponseEntity<ApiResponse<Integer>> getShoppingCartCount(@RequestHeader("X-User-Id") String userId,  @RequestHeader("X-User-Role") String role) {
        if (role.equals("ROLE_USER")){
            Integer count = shoppingCartService.getShoppingCart(Long.valueOf(userId)).getItems().size();
            return  ResponseEntity.ok(new ApiResponse<>(true, "Cart fetched successfully", count));
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

    @DeleteMapping("/remove-item/{id}")
    public ResponseEntity<ApiResponse<ShoppingCartResponse>> removeItem(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String role, @PathVariable Long id){
        if (role.equals("ROLE_USER")){
            ShoppingCart shoppingCart = shoppingCartService.removeItem(Long.valueOf(userId), id);

            return ResponseEntity.ok(new ApiResponse<>(true, "Item removed", shoppingCartMapper.toResponse(shoppingCart)));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false, "Not customer user", null));
    }

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<?>> checkout(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String role, @RequestBody CardInfoRequest cardInfoRequest, @RequestHeader("X-User-Username") String username){
        if (role.equals("ROLE_USER")){
            int total = shoppingCartService.getShoppingCart(Long.valueOf(userId)).getItems().stream().map(cartItem -> cartItem.getMenuItemSnapshot().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))).mapToInt(BigDecimal::intValue).sum();
            try {kafkaProducer.sendCheckoutEvent(cardInfoRequest, total, username, Long.valueOf(userId));}
            catch (Exception e ){
                System.out.println(e.getMessage());
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Checkout sent", null));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false, "Not customer user", null));
    }
}
