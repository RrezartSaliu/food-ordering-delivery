package org.example.order_service.Mapper;

import org.example.order_service.Domain.DTO.ShoppingCartResponse;
import org.example.order_service.Domain.model.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    ShoppingCartResponse toResponse(ShoppingCart shoppingCart);
}
