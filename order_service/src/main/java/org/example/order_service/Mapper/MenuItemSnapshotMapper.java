package org.example.order_service.Mapper;

import org.example.order_service.Domain.DTO.MenuItemSnapshotResponse;
import org.example.order_service.Domain.model.MenuItemSnapshot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuItemSnapshotMapper {
    MenuItemSnapshotResponse toResponse(MenuItemSnapshot menuItemSnapshot);
}
