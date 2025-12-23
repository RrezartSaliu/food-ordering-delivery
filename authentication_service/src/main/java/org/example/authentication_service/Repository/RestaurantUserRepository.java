package org.example.authentication_service.Repository;

import org.example.authentication_service.Domain.model.AppUser;
import org.example.authentication_service.Domain.model.RestaurantProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantUserRepository extends JpaRepository<RestaurantProfile, Long> {
    RestaurantProfile findByAppUser(AppUser appUser);
    List<RestaurantProfile> findAllByVerified(Boolean verified);
}
