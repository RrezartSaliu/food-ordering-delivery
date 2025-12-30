package org.example.notification_service.Listener;

import lombok.RequiredArgsConstructor;
import org.example.OrderCreatedEvent;
import org.example.notification_service.FeignClient.NotificationClient;
import org.example.notification_service.WsEvent.OrderCreatedWs;
import org.example.notification_service.util.WebSocketHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderCreationListener {
    private final NotificationClient notificationClient;
    private final WebSocketHandler webSocketHandler;


    @RabbitListener(queues = "order.queue")
    public void notifyRestaurant(OrderCreatedEvent orderCreatedEvent) {
        List<Long> driverIds = notificationClient.driversIds(String.valueOf(orderCreatedEvent.getRestaurantId()));
        try {
            webSocketHandler.sendToUser(String.valueOf(orderCreatedEvent.getRestaurantId()), new OrderCreatedWs("order-created", orderCreatedEvent.getOrderId()));
            for(Long driverId : driverIds){
                webSocketHandler.sendToUser(String.valueOf(driverId), new OrderCreatedWs("order-created", orderCreatedEvent.getOrderId()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
