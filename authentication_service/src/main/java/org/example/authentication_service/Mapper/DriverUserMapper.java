package org.example.authentication_service.Mapper;

import org.example.authentication_service.Domain.DTO.DriverUserResponse;
import org.example.authentication_service.Domain.model.DriverProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DriverUserMapper {
    @Mapping(source = "appUser.email", target = "email")
    @Mapping(source = "appUser.role", target = "role")
    DriverUserResponse toResponse(DriverProfile driverProfile);
}
