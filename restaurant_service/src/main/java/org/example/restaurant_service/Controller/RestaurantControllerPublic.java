package org.example.restaurant_service.Controller;

import lombok.RequiredArgsConstructor;
import org.example.ApiResponse;
import org.example.restaurant_service.Domain.DTO.MenuItemResponse;
import org.example.restaurant_service.Domain.model.Category;
import org.example.restaurant_service.Domain.model.MenuItem;
import org.example.restaurant_service.Mapper.MenuItemMapper;
import org.example.restaurant_service.Service.MenuItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController@RequestMapping("/restaurant/public")
@RequiredArgsConstructor
public class RestaurantControllerPublic {
    private final MenuItemService menuItemService;
    private final MenuItemMapper menuItemMapper;

    @GetMapping("/restaurant-items")
    public ResponseEntity<ApiResponse<List<MenuItemResponse>>> getRestaurantItems(@RequestParam Long restaurantId) {
        List<MenuItem> menuItems = menuItemService.findByRestaurant(restaurantId);
        List<MenuItemResponse> menuItemResponses = menuItems.stream().map(menuItemMapper::toResponse).toList();
        return ResponseEntity.ok(new ApiResponse<>(true, "Restaurant Items", menuItemResponses));
    }

    @GetMapping("/by-category")
    public ResponseEntity<ApiResponse<List<MenuItemResponse>>> getByCategory(@RequestParam Category category) {
        List<MenuItem> menuItems = menuItemService.findByCategory(category);
        List<MenuItemResponse> menuItemResponses = menuItems.stream().map(menuItemMapper::toResponse).toList();
        return ResponseEntity.ok(new ApiResponse<>(true, "Items by category", menuItemResponses));
    }
}
