package org.example.authentication_service.Controller;

import lombok.RequiredArgsConstructor;
import org.example.authentication_service.Domain.model.AppUser;
import org.example.authentication_service.Domain.model.CustomerProfile;
import org.example.authentication_service.Service.CustomerUserService;
import org.example.authentication_service.Service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CustomerUserService customerUserService;

    @GetMapping("/get-user")
    public AppUser getUserByEmail(@RequestParam String email){
        return userService.findByEmail(email);
    }

    @GetMapping("/hello")
    public String hello(@RequestHeader("X-User-Username") String username, @RequestHeader("X-User-Id") String id, @RequestHeader("X-User-Role") String role){
        if(role.equals("ROLE_USER")){
            Optional<CustomerProfile> customerProfile = customerUserService.findById(Long.valueOf(id));
            return "Hello"+ (customerProfile.map(profile -> profile.getFirstName() + " " + profile.getLastName()).orElse("UNKNOWN"));
        }
        return "Hello" + username;
    }
}
