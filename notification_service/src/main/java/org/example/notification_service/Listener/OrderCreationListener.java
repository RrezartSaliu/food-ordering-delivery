package org.example.notification_service.Listener;

import lombok.RequiredArgsConstructor;
import org.example.OrderCreatedEvent;
import org.example.notification_service.util.WebSocketHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreationListener {
    private final WebSocketHandler webSocketHandler;

    @RabbitListener(queues = "order.queue")
    public void notifyRestaurant(OrderCreatedEvent orderCreatedEvent) {
        System.out.println(orderCreatedEvent.getRestaurantId().toString() +"  ---    "+orderCreatedEvent.getOrderId().toString());
        try {
            String wsMessage =
                    "{\"type\":\"order-created\",\"orderId\":\"" + orderCreatedEvent.getOrderId() + "\"}";
            webSocketHandler.sendToUser(String.valueOf(orderCreatedEvent.getRestaurantId()), wsMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
