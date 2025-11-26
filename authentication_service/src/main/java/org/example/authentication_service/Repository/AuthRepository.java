package org.example.authentication_service.Repository;

import org.example.authentication_service.Domain.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
}
