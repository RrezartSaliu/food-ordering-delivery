package org.example.restaurant_service.Mapper;

import org.example.restaurant_service.Domain.DTO.MenuItemResponse;
import org.example.restaurant_service.Domain.model.MenuItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    MenuItemResponse toResponse(MenuItem menuItem);
}
