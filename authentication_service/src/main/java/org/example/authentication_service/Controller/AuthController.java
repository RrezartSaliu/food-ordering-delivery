package org.example.authentication_service.Controller;

import org.example.ApiResponse;
import org.example.authentication_service.Domain.DTO.AuthRequest;
import org.example.authentication_service.Domain.DTO.RegisterDriverRequest;
import org.example.authentication_service.Domain.DTO.RegisterRestaurantRequest;
import org.example.authentication_service.Domain.DTO.RegisterUserRequest;
import org.example.authentication_service.Domain.model.DriverProfile;
import org.example.authentication_service.Security.AppUserService;
import org.example.authentication_service.Security.JwtService;
import org.example.authentication_service.Service.DriverUserService;
import org.example.authentication_service.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final DriverUserService driverUserService;

    public AuthController(JwtService jwtService, AppUserService appUserService, AuthenticationManager authenticationManager, UserService userService, DriverUserService driverUserService) {
        this.jwtService = jwtService;
        this.appUserService = appUserService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.driverUserService = driverUserService;
    }


    @PostMapping("/register-customer-user")
    public ResponseEntity<ApiResponse<String>> registerCustomerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        String message = appUserService.addCustomerUser(registerUserRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, message, null));
    }

    @PostMapping("/register-restaurant-user")
    public ResponseEntity<ApiResponse<String>> registerRestaurantUser(@RequestBody RegisterRestaurantRequest registerRestaurantRequest) {
        String message = appUserService.addRestaurantUser(registerRestaurantRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, message, null));
    }

    @PostMapping("/driver/register-driver-user")
    public ResponseEntity<ApiResponse<String>> registerDriverUser(@RequestHeader("X-User-Id") String id, @RequestHeader("X-User-Role") String role, @RequestBody RegisterDriverRequest registerDriverRequest) {
        if( role.equals("ROLE_RESTAURANT")) {
            String message = appUserService.addDriverUser(registerDriverRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, message, null));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false, "Unauthorized", null));
    }

    @PostMapping("/generateToken")
    public ResponseEntity<ApiResponse<String>> generateToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            String token = jwtService.generateToken(userService.findByEmail(authRequest.getEmail()));
            return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{restaurantId}/workers/{userId}/exists")
    public boolean isWorker(@PathVariable Long restaurantId, @PathVariable Long userId) {
        return driverUserService.findAllByRestaurantProfile_Id(restaurantId).stream().map(DriverProfile::getId).toList().contains(userId);
    }

    @GetMapping("/drivers/{restaurantId}")
    public List<Long> driversIds (@PathVariable Long restaurantId) {
        return driverUserService.findAllByRestaurantProfile_Id(restaurantId).stream().map(DriverProfile::getId).toList();
    }
}
