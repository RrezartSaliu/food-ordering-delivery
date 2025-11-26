package org.example.authentication_service.Service.implementation;

import org.example.authentication_service.Domain.model.RestaurantProfile;
import org.example.authentication_service.Repository.RestaurantUserRepository;
import org.example.authentication_service.Service.RestaurantUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantUserServiceImpl implements RestaurantUserService {
    private final RestaurantUserRepository restaurantUserRepository;

    public RestaurantUserServiceImpl(RestaurantUserRepository restaurantUserRepository) {
        this.restaurantUserRepository = restaurantUserRepository;
    }

    @Override
    public Optional<RestaurantProfile> findById(Long id) {
        return restaurantUserRepository.findById(id);
    }
}
