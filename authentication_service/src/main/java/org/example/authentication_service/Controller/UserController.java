package org.example.authentication_service.Controller;

import lombok.RequiredArgsConstructor;
import org.example.ApiResponse;
import org.example.authentication_service.Domain.DTO.AdminUserResponse;
import org.example.authentication_service.Domain.DTO.CustomerUserResponse;
import org.example.authentication_service.Domain.DTO.RestaurantUserResponse;
import org.example.authentication_service.Domain.model.AdminProfile;
import org.example.authentication_service.Domain.model.AppUser;
import org.example.authentication_service.Domain.model.CustomerProfile;
import org.example.authentication_service.Domain.model.RestaurantProfile;
import org.example.authentication_service.Mapper.AdminUserMapper;
import org.example.authentication_service.Mapper.CustomerUserMapper;
import org.example.authentication_service.Mapper.RestaurantUserMapper;
import org.example.authentication_service.Service.AdminUserService;
import org.example.authentication_service.Service.CustomerUserService;
import org.example.authentication_service.Service.RestaurantUserService;
import org.example.authentication_service.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
    private final AdminUserService adminUserService;
    private final AdminUserMapper adminUserMapper;

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
        else if(role.equals("ROLE_ADMIN")){
            AdminProfile adminProfile = adminUserService.findById(Long.valueOf(id));
            if (adminProfile != null) {
                AdminUserResponse adminUserResponse = adminUserMapper.toResponse(adminProfile);
                return ResponseEntity.ok(new ApiResponse<>(true, "admin profile", adminUserResponse));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "User not found", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, null, null));
    }

    @PostMapping("/admin/verify-restaurant")
    public ResponseEntity<ApiResponse<Long>> verifyRestaurant(@RequestHeader("X-User-Role") String role, @RequestBody RestaurantProfile restaurantProfile){
        if(role.equals("ROLE_ADMIN")) {
            String message = restaurantUserService.verifyRestaurantProfile(restaurantProfile.getId());
            return ResponseEntity.ok(new ApiResponse<>(true, "Restaurant profile verified", restaurantProfile.getId()));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, "not admin", null));
    }

    @GetMapping("/admin/get-restaurants")
    public ResponseEntity<ApiResponse<List<RestaurantUserResponse>>> getRestaurants(@RequestHeader("X-User-Role") String role){
        if (role.equals("ROLE_ADMIN")){
            List<RestaurantProfile> restaurantProfiles = restaurantUserService.findAll();
            List<RestaurantUserResponse> restaurantUserResponses= new ArrayList<>();
            for (RestaurantProfile restaurantProfile : restaurantProfiles) {
                restaurantUserResponses.add(restaurantUserMapper.toResponse(restaurantProfile));
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "restaurants fetched for admin", restaurantUserResponses));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "not admin", null));
    }

    @GetMapping("/get-restaurants")
    public ResponseEntity<ApiResponse<List<RestaurantUserResponse>>> getVerifiedRestaurants(){
        List<RestaurantProfile> restaurantProfiles = restaurantUserService.findAllByVerified(true);
        List<RestaurantUserResponse> restaurantUserResponses= new ArrayList<>();
        for (RestaurantProfile restaurantProfile : restaurantProfiles) {
            restaurantUserResponses.add(restaurantUserMapper.toResponse(restaurantProfile));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "restaurants fetched", restaurantUserResponses));
    }
}
