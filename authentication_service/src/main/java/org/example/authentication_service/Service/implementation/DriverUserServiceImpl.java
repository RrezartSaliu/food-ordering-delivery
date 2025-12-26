package org.example.authentication_service.Service.implementation;

import lombok.RequiredArgsConstructor;
import org.example.authentication_service.Domain.model.DriverProfile;
import org.example.authentication_service.Repository.DriverUserRepository;
import org.example.authentication_service.Service.DriverUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverUserServiceImpl implements DriverUserService {
    private final DriverUserRepository driverUserRepository;

    @Override
    public List<DriverProfile> findAllByRestaurantProfile_Id(Long id) {
        return driverUserRepository.findAllByRestaurantProfile_Id(id);
    }

    @Override
    public DriverProfile findById(Long id) {
        return driverUserRepository.findById(id).orElse(null);
    }
}
