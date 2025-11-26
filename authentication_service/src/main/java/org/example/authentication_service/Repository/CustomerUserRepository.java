package org.example.authentication_service.Repository;

import org.example.authentication_service.Domain.model.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerUserRepository extends JpaRepository<CustomerProfile, Long> {
}
