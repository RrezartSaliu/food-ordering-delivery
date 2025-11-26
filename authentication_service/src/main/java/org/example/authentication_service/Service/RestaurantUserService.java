package org.example.authentication_service.Service;


import org.example.authentication_service.Domain.model.RestaurantProfile;

import java.util.Optional;

public interface RestaurantUserService {
    Optional<RestaurantProfile> findById(Long id);
}
