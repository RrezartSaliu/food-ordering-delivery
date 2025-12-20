package org.example.restaurant_service.Controller;

import lombok.RequiredArgsConstructor;
import org.example.ApiResponse;
import org.example.restaurant_service.Domain.DTO.MenuItemRequest;
import org.example.restaurant_service.Domain.DTO.MenuItemResponse;
import org.example.restaurant_service.Domain.model.Category;
import org.example.restaurant_service.Domain.model.MenuItem;
import org.example.restaurant_service.Mapper.MenuItemMapper;
import org.example.restaurant_service.Service.MenuItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurant/protected")
@RequiredArgsConstructor
public class RestaurantControllerProtected {
    private final MenuItemService menuItemService;
    private final MenuItemMapper menuItemMapper;

    @PostMapping("/create-item")
    public ResponseEntity<ApiResponse<MenuItemResponse>> createMenuItem(@RequestHeader("X-User-Username") String username, @RequestHeader("X-User-Id") String id, @RequestHeader("X-User-Role") String role, @RequestBody MenuItemRequest menuItemRequest) {
        if (role.equals("ROLE_RESTAURANT")) {
            try {
                MenuItem menuItem = menuItemService.create(Long.valueOf(id), menuItemRequest.getName(), menuItemRequest.getPrice(), menuItemRequest.getCategory());
                return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Item added successfully", menuItemMapper.toResponse(menuItem)));
            }
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, e.getMessage(), null));
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, "Not restaurant user", null));
    }

    @PostMapping("/update-item")
    public ResponseEntity<ApiResponse<MenuItemResponse>> updateMenuItem(@RequestHeader("X-User-Username") String username, @RequestHeader("X-User-Id") String id, @RequestHeader("X-User-Role") String role,  @RequestBody MenuItemRequest menuItemRequest, @RequestParam Long itemId) {
        if (role.equals("ROLE_RESTAURANT")) {
            MenuItem updated = menuItemService.update(itemId, menuItemRequest.getName(), menuItemRequest.getPrice(), menuItemRequest.getCategory(), Long.valueOf(id));
            return ResponseEntity.ok(new ApiResponse<>(true, "Item updated", menuItemMapper.toResponse(updated)));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, "Not restaurant user", null));
    }

    @DeleteMapping("/delete-item/{itemId}")
    public ResponseEntity<ApiResponse<MenuItemResponse>> deleteItem(@RequestHeader("X-User-Username") String username, @RequestHeader("X-User-Id") String id, @RequestHeader("X-User-Role") String role, @PathVariable Long itemId) {
        if (role.equals("ROLE_RESTAURANT")) {
            MenuItem removedItem = menuItemService.deleteById(itemId, Long.valueOf(id));
            return ResponseEntity.ok(new ApiResponse<>(true, "Item removed", menuItemMapper.toResponse(removedItem)));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, "Not restaurant user", null));
    }

    @GetMapping("/my-items-grouped")
    public ResponseEntity<ApiResponse<Map<Category, List<MenuItemResponse>>>> myItemsGrouped (@RequestHeader("X-User-Role") String role, @RequestHeader("X-User-Id") String id){
        if (role.equals("ROLE_RESTAURANT")) {
            List<MenuItem> menuItems = menuItemService.findMyRestaurantItems(Long.valueOf(id));

            Map<Category, List<MenuItemResponse>> grouped = menuItems.stream().map(menuItemMapper::toResponse).collect(Collectors.groupingBy(MenuItemResponse::getCategory));

            return ResponseEntity.ok(new ApiResponse<>(true, "Items fetched", grouped));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, "Not restaurant user", null));
    }
}
