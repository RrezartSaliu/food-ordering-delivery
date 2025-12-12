package org.example.authentication_service.Mapper;

import org.example.authentication_service.Domain.DTO.CustomerUserResponse;
import org.example.authentication_service.Domain.model.CustomerProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerUserMapper {
    @Mapping(source = "appUser.email", target = "email")
    @Mapping(source = "appUser.role", target = "role")
    CustomerUserResponse toResponse(CustomerProfile customerProfile);
}
