package org.example.order_service.Mapper;

import org.example.order_service.Domain.DTO.OrderResponse;
import org.example.order_service.Domain.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toResponse(Order order);
}
