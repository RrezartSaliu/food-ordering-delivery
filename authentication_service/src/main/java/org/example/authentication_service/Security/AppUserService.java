package org.example.authentication_service.Security;

import org.example.authentication_service.Domain.DTO.RegisterDriverRequest;
import org.example.authentication_service.Domain.DTO.RegisterRestaurantRequest;
import org.example.authentication_service.Domain.DTO.RegisterUserRequest;
import org.example.authentication_service.Domain.model.*;
import org.example.authentication_service.Repository.AuthRepository;
import org.example.authentication_service.Repository.CustomerUserRepository;
import org.example.authentication_service.Repository.DriverUserRepository;
import org.example.authentication_service.Repository.RestaurantUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerUserRepository customerUserRepository;
    private final RestaurantUserRepository restaurantUserRepository;
    private final DriverUserRepository driverUserRepository;

    public AppUserService(AuthRepository authRepository, PasswordEncoder passwordEncoder, CustomerUserRepository customerUserRepository, RestaurantUserRepository restaurantUserRepository, DriverUserRepository driverUserRepository) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerUserRepository = customerUserRepository;
        this.restaurantUserRepository = restaurantUserRepository;
        this.driverUserRepository = driverUserRepository;
    }


    public String addCustomerUser (RegisterUserRequest registerUserRequest) {
        AppUser appUser = new AppUser();
        appUser.setEmail(registerUserRequest.getEmail());
        appUser.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        appUser.setRole(Role.ROLE_USER);
        AppUser savedUser = authRepository.save(appUser);

        CustomerProfile customerProfile = new CustomerProfile();
        customerProfile.setFirstName(registerUserRequest.getFirstName());
        customerProfile.setLastName(registerUserRequest.getLastName());
        customerProfile.setId(savedUser.getId());
        customerProfile.setAppUser(savedUser);
        customerUserRepository.save(customerProfile);
        return "CustomerUser added successfully";
    }

    public String addDriverUser (RegisterDriverRequest registerDriverRequest) {
        AppUser appUser = new AppUser();
        appUser.setEmail(registerDriverRequest.getEmail());
        appUser.setPassword(passwordEncoder.encode(registerDriverRequest.getPassword()));
        appUser.setRole(Role.ROLE_DRIVER);
        AppUser savedUser = authRepository.save(appUser);

        DriverProfile driverProfile = new DriverProfile();
        driverProfile.setFirstName(registerDriverRequest.getFirstName());
        driverProfile.setLastName(registerDriverRequest.getLastName());
        driverProfile.setAppUser(savedUser);
        driverProfile.setId(savedUser.getId());
        driverProfile.setVehicle(registerDriverRequest.getVehicle());
        driverUserRepository.save(driverProfile);
        return "DriverUser added successfully";
    }

    public String addRestaurantUser (RegisterRestaurantRequest registerRestaurantRequest) {
        AppUser appUser = new AppUser();
        appUser.setEmail(registerRestaurantRequest.getEmail());
        appUser.setPassword(passwordEncoder.encode(registerRestaurantRequest.getPassword()));
        appUser.setRole(Role.ROLE_RESTAURANT);
        AppUser savedUser = authRepository.save(appUser);

        RestaurantProfile restaurantProfile = new RestaurantProfile();
        restaurantProfile.setAppUser(savedUser);
        restaurantProfile.setId(savedUser.getId());
        restaurantProfile.setName(registerRestaurantRequest.getName());
        restaurantProfile.setAddress(registerRestaurantRequest.getAddress());
        restaurantUserRepository.save(restaurantProfile);
        return "RestaurantUser added successfully";
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = authRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();
    }
}
