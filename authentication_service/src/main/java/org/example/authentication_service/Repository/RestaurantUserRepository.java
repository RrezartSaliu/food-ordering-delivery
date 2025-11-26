package org.example.authentication_service.Repository;

import org.example.authentication_service.Domain.model.RestaurantProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantUserRepository extends JpaRepository<RestaurantProfile, Long> {
}
