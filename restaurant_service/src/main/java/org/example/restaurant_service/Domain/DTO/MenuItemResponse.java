package org.example.restaurant_service.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.restaurant_service.Domain.model.Category;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemResponse {
    private Long id;
    private BigDecimal price;
    private Category category;
    private Long restaurantId;
    private String name;
}
