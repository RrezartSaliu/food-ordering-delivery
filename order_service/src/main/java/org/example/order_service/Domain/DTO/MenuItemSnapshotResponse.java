package org.example.order_service.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MenuItemSnapshotResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private String category;
}
