package org.example.order_service.Mapper;

import org.example.order_service.Domain.DTO.CartItemResponse;
import org.example.order_service.Domain.model.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = MenuItemSnapshotMapper.class)
public interface CartItemMapper {
    CartItemResponse toResponse (CartItem cartItem);
}
