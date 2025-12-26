package org.example.authentication_service.Repository;

import org.example.authentication_service.Domain.model.DriverProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverUserRepository extends JpaRepository<DriverProfile, Long> {
    List<DriverProfile> findAllByRestaurantProfile_Id(Long id);
}
