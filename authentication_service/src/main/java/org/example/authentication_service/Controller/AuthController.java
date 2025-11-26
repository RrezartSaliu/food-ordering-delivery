package org.example.authentication_service.Controller;

import org.example.authentication_service.Domain.DTO.AuthRequest;
import org.example.authentication_service.Domain.DTO.RegisterDriverRequest;
import org.example.authentication_service.Domain.DTO.RegisterRestaurantRequest;
import org.example.authentication_service.Domain.DTO.RegisterUserRequest;
import org.example.authentication_service.Security.AppUserService;
import org.example.authentication_service.Security.JwtService;
import org.example.authentication_service.Service.UserService;
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
    public String registerCustomerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        return appUserService.addCustomerUser(registerUserRequest);
    }

    @PostMapping("/register-restaurant-user")
    public String registerRestaurantUser(@RequestBody RegisterRestaurantRequest registerRestaurantRequest) {
        return appUserService.addRestaurantUser(registerRestaurantRequest);
    }

    @PostMapping("/register-driver-user")
    public String registerDriverUser(@RequestBody RegisterDriverRequest registerDriverRequest) {
        return appUserService.addDriverUser(registerDriverRequest);
    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );


            return jwtService.generateToken(userService.findByEmail(authRequest.getEmail()));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
