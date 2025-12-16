package org.example.order_service.Consumer;

import org.example.MenuItemEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @KafkaListener(topics = "MENU_ITEM_CREATED", groupId= "order-service-group")
    public void consumeMenuItemEvent(MenuItemEvent menuItemEvent) {
        System.out.println(menuItemEvent);
    }
}
