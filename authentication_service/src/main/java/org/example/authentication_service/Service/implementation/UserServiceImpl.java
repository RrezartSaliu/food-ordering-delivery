package org.example.authentication_service.Service.implementation;

import lombok.RequiredArgsConstructor;
import org.example.authentication_service.Domain.model.AppUser;
import org.example.authentication_service.Repository.UserRepository;
import org.example.authentication_service.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public AppUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        return userRepository.findById(id);
    }

}
