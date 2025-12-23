package org.example.authentication_service.Repository;

import org.example.authentication_service.Domain.model.AdminProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminProfile, Long> {
}
