package org.example.authentication_service.Service;

import org.example.authentication_service.Domain.model.DriverProfile;

import java.util.List;

public interface DriverUserService {
    List<DriverProfile> findAllByRestaurantProfile_Id(Long id);
    DriverProfile findById(Long id);
}
