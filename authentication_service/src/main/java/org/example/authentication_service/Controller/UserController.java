package org.example.authentication_service.Controller;

import lombok.RequiredArgsConstructor;
import org.example.authentication_service.Domain.DTO.CustomerUserResponse;
import org.example.authentication_service.Domain.DTO.RestaurantUserResponse;
import org.example.authentication_service.Domain.model.AppUser;
import org.example.authentication_service.Domain.model.CustomerProfile;
import org.example.authentication_service.Domain.model.RestaurantProfile;
import org.example.authentication_service.Mapper.CustomerUserMapper;
import org.example.authentication_service.Mapper.RestaurantUserMapper;
import org.example.authentication_service.Service.CustomerUserService;
import org.example.authentication_service.Service.RestaurantUserService;
import org.example.authentication_service.Service.UserService;
import org.example.authentication_service.Util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CustomerUserService customerUserService;
    private final CustomerUserMapper customerUserMapper;
    private final RestaurantUserService restaurantUserService;
    private final RestaurantUserMapper restaurantUserMapper;

    @GetMapping("/get-user")
    public AppUser getUserByEmail(@RequestParam String email){
        return userService.findByEmail(email);
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<?>> profile(@RequestHeader("X-User-Username") String username, @RequestHeader("X-User-Id") String id, @RequestHeader("X-User-Role") String role){
        if(role.equals("ROLE_USER")){
            Optional<CustomerProfile> customerProfile = customerUserService.findById(Long.valueOf(id));
            CustomerUserResponse customerUserResponse = customerUserMapper.toResponse(customerProfile.orElse(null));
            return ResponseEntity.ok(new ApiResponse<>(true, "user profile", customerUserResponse));
        }
        else if(role.equals("ROLE_RESTAURANT")){
            Optional<RestaurantProfile> restaurantProfile = restaurantUserService.findById(Long.valueOf(id));
            RestaurantUserResponse restaurantUserResponse = restaurantUserMapper.toResponse(restaurantProfile.orElse(null));
            return ResponseEntity.ok(new ApiResponse<>(true, "restaurant profile", restaurantUserResponse));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, null, null));
    }
}
