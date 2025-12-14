package org.example.restaurant_service.Mapper;

import org.example.restaurant_service.Domain.model.MenuItem;
import org.mapstruct.Mapper;

@Mapper
public interface MenuItemMapper {
    MenuItem toResponse (MenuItem menuItem);
}
