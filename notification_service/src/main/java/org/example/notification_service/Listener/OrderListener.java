package org.example.notification_service.Listener;

import lombok.RequiredArgsConstructor;
import org.example.OrderCreatedEvent;
import org.example.OrderDeliveredEvent;
import org.example.notification_service.FeignClient.AuthClient;
import org.example.notification_service.WsEvent.OrderCreatedWs;
import org.example.notification_service.WsEvent.OrderDeliveredWs;
import org.example.notification_service.util.WebSocketHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderListener {
    private final AuthClient authClient;
    private final WebSocketHandler webSocketHandler;


    @RabbitListener(queues = "order.queue")
    public void notifyRestaurant(OrderCreatedEvent orderCreatedEvent) {
        List<Long> driverIds = authClient.driversIds(String.valueOf(orderCreatedEvent.getRestaurantId()));
        try {
            webSocketHandler.sendToUser(String.valueOf(orderCreatedEvent.getRestaurantId()), new OrderCreatedWs("order-created", orderCreatedEvent.getOrderId()));
            for(Long driverId : driverIds){
                webSocketHandler.sendToUser(String.valueOf(driverId), new OrderCreatedWs("order-created", orderCreatedEvent.getOrderId()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "order.delivered.queue")
    public void notifyOrderDelivered(OrderDeliveredEvent orderDeliveredEvent) {
        OrderDeliveredWs orderDeliveredWs = new OrderDeliveredWs();
        orderDeliveredWs.setOrderId(orderDeliveredEvent.getOrderId());
        orderDeliveredWs.setType("ORDER_DELIVERED");
        try {
            webSocketHandler.sendToUser(String.valueOf(orderDeliveredEvent.getRestaurantId()), orderDeliveredWs);
            webSocketHandler.sendToUser(String.valueOf(orderDeliveredEvent.getUserId()), orderDeliveredWs);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
