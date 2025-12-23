package org.example.authentication_service.Service.implementation;

import lombok.RequiredArgsConstructor;
import org.example.authentication_service.Domain.model.AdminProfile;
import org.example.authentication_service.Repository.AdminUserRepository;
import org.example.authentication_service.Service.AdminUserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final AdminUserRepository adminUserRepository;

    @Override
    public AdminProfile findById(Long id) {
        return adminUserRepository.findById(id).orElse(null);
    }
}
