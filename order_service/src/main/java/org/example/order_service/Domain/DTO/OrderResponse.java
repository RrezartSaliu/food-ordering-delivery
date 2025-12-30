package org.example.order_service.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.order_service.Domain.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private BigDecimal amount;
    private LocalDateTime orderDateTime;
    private Long userId;
    private OrderStatus orderStatus;
    private Long driverId;
    private Long restaurantId;
    private String address;
    private Double longitude;
    private Double latitude;
}
