package org.example.authentication_service.Mapper;

import org.example.authentication_service.Domain.DTO.RestaurantUserResponse;
import org.example.authentication_service.Domain.model.RestaurantProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestaurantUserMapper {
    @Mapping(source = "appUser.email", target = "email")
    @Mapping(source = "appUser.role", target = "role")
    RestaurantUserResponse toResponse(RestaurantProfile restaurantProfile);
}
