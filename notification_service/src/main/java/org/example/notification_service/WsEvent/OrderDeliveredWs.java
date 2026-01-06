package org.example.notification_service.WsEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDeliveredWs {
    String type;
    Long orderId;
}
