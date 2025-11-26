package org.example.authentication_service.Repository;

import org.example.authentication_service.Domain.model.DriverProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverUserRepository extends JpaRepository<DriverProfile, Long> {
}
