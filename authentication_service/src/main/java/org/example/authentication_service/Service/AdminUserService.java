package org.example.authentication_service.Service;

import org.example.authentication_service.Domain.model.AdminProfile;

public interface AdminUserService {
    AdminProfile findById(Long id);
}
