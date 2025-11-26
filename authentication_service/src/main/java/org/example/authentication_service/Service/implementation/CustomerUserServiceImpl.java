package org.example.authentication_service.Service.implementation;

import org.example.authentication_service.Domain.DTO.RegisterUserRequest;
import org.example.authentication_service.Domain.model.CustomerProfile;
import org.example.authentication_service.Repository.CustomerUserRepository;
import org.example.authentication_service.Service.CustomerUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerUserServiceImpl implements CustomerUserService {
    private final CustomerUserRepository customerUserRepository;

    public CustomerUserServiceImpl(CustomerUserRepository customerUserRepository) {
        this.customerUserRepository = customerUserRepository;
    }

    @Override
    public Optional<CustomerProfile> findById(Long id) {
        return customerUserRepository.findById(id);
    }

}
