package org.example.authentication_service.Service;

import org.example.authentication_service.Domain.DTO.RegisterUserRequest;
import org.example.authentication_service.Domain.model.CustomerProfile;

import java.util.Optional;

public interface CustomerUserService {
    Optional<CustomerProfile> findById(Long id);
}
