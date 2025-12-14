package org.example.authentication_service.Controller;

import org.example.ApiResponse;
import org.example.authentication_service.Domain.DTO.AuthRequest;
import org.example.authentication_service.Domain.DTO.RegisterDriverRequest;
import org.example.authentication_service.Domain.DTO.RegisterRestaurantRequest;
import org.example.authentication_service.Domain.DTO.RegisterUserRequest;
import org.example.authentication_service.Security.AppUserService;
import org.example.authentication_service.Security.JwtService;
import org.example.authentication_service.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(JwtService jwtService, AppUserService appUserService, AuthenticationManager authenticationManager, UserService userService) {
        this.jwtService = jwtService;
        this.appUserService = appUserService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
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

    @PostMapping("/register-driver-user")
    public ResponseEntity<ApiResponse<String>> registerDriverUser(@RequestBody RegisterDriverRequest registerDriverRequest) {
        String message = appUserService.addDriverUser(registerDriverRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, message, null));
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
}
