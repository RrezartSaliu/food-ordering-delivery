package org.example.authentication_service.Service;

import org.example.authentication_service.Domain.model.RestaurantProfile;

import java.util.List;
import java.util.Optional;

public interface RestaurantUserService {
    Optional<RestaurantProfile> findById(Long id);
    String verifyRestaurantProfile(Long id);
    List<RestaurantProfile> findAll();
    List<RestaurantProfile> findAllByVerified(Boolean verified);
}
