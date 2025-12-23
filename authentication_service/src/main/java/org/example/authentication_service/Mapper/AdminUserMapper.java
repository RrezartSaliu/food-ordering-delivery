package org.example.authentication_service.Mapper;

import org.example.authentication_service.Domain.DTO.AdminUserResponse;
import org.example.authentication_service.Domain.model.AdminProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminUserMapper {
    @Mapping(source = "appUser.email", target = "email")
    @Mapping(source = "appUser.role", target = "role")
    AdminUserResponse toResponse(AdminProfile adminProfile);
}
