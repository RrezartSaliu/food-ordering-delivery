package org.example.restaurant_service.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.restaurant_service.Domain.model.Category;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MenuItemRequest {
    private String name;
    private BigDecimal price;
    private Category category;
}
