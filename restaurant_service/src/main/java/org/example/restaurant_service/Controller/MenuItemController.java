package org.example.restaurant_service.Controller;

import lombok.RequiredArgsConstructor;
import org.example.ApiResponse;
import org.example.restaurant_service.Domain.DTO.MenuItemRequest;
import org.example.restaurant_service.Domain.model.MenuItem;
import org.example.restaurant_service.Service.MenuItemService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu-item")
@RequiredArgsConstructor
public class MenuItemController {
    private final MenuItemService menuItemService;

    @PostMapping
    public ApiResponse<String> createMenuItem(@RequestHeader("X-User-Username") String username, @RequestHeader("X-User-Id") String id, @RequestHeader("X-User-Role") String role, @RequestBody MenuItemRequest menuItemRequest) {
        if (role.equals("ROLE_RESTAURANT")) {
            try {
                menuItemService.create(Long.valueOf(id), menuItemRequest.getName(), menuItemRequest.getPrice(), menuItemRequest.getCategory());
                return new ApiResponse<>(true, "Item added successfully", "Item created successfully");
            }
            catch (Exception e) {
                return new ApiResponse<>(false, e.getMessage(), null);
            }
        }
        return new ApiResponse<>(false, "Not restaurant user", null);
    }
}
