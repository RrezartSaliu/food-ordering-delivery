package org.example.authentication_service.Service;

import org.example.authentication_service.Domain.model.AppUser;

import java.util.Optional;

public interface UserService {
    AppUser findByEmail(String email);
    Optional<AppUser> findById(Long id);
}
