package org.example.authentication_service.Service.implementation;

import org.example.authentication_service.Domain.model.RestaurantProfile;
import org.example.authentication_service.Repository.RestaurantUserRepository;
import org.example.authentication_service.Service.RestaurantUserService;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public String verifyRestaurantProfile(Long id) {
        Optional<RestaurantProfile> restaurantProfile = this.findById(id);
        if (restaurantProfile.isPresent()) {
            restaurantProfile.get().setVerified(true);
            restaurantUserRepository.save(restaurantProfile.get());
            return "Restaurant verified";
        }
        return "Restaurant not found";
    }

    @Override
    public List<RestaurantProfile> findAll() {
        return restaurantUserRepository.findAll();
    }

    @Override
    public List<RestaurantProfile> findAllByVerified(Boolean verified) {
        return restaurantUserRepository.findAllByVerified(verified);
    }
}
