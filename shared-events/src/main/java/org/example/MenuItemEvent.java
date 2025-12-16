package org.example;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemEvent {
    private MenuItemEventType menuItemEventType;
    private Long menuItemId;
    private Long restaurantId;
    private String name;
    private BigDecimal price;
    private String category;
    private boolean active;
}
