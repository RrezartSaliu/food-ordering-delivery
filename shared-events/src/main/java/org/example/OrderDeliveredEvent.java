package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDeliveredEvent {
    private Long orderId;
    private Long restaurantId;
    private Long userId;
}
