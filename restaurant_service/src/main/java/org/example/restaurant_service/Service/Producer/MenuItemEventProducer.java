package org.example.restaurant_service.Service.Producer;

import lombok.RequiredArgsConstructor;
import org.example.MenuItemEvent;
import org.example.MenuItemEventType;
import org.example.restaurant_service.Domain.model.MenuItem;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuItemEventProducer {
    private final KafkaTemplate<String, MenuItemEvent> kafkaTemplate;

    public void createMenuItem(MenuItem menuItem){
        MenuItemEvent event = MenuItemEvent.builder()
                .menuItemEventType(MenuItemEventType.valueOf("MENU_ITEM_CREATED"))
                .menuItemId(menuItem.getId())
                .restaurantId(menuItem.getRestaurantId())
                .name(menuItem.getName())
                .price(menuItem.getPrice())
                .category(menuItem.getCategory().name())
                .active(true)
                .build();

        kafkaTemplate.send("menu-item-events", event);
    }

    public void deleteMenuItem(MenuItem menuItem){
        MenuItemEvent event = MenuItemEvent.builder()
                .menuItemEventType(MenuItemEventType.valueOf("MENU_ITEM_DELETED"))
                .menuItemId(menuItem.getId())
                .restaurantId(menuItem.getRestaurantId())
                .name(menuItem.getName())
                .price(menuItem.getPrice())
                .category(menuItem.getCategory().name())
                .build();

        kafkaTemplate.send("menu-item-events", event);
    }

    public void updateMenuItem(MenuItem menuItem){
        MenuItemEvent event = MenuItemEvent.builder()
                .menuItemEventType(MenuItemEventType.valueOf("MENU_ITEM_UPDATED"))
                .menuItemId(menuItem.getId())
                .restaurantId(menuItem.getRestaurantId())
                .name(menuItem.getName())
                .price(menuItem.getPrice())
                .category(menuItem.getCategory().name())
                .active(true)
                .build();

        kafkaTemplate.send("menu-item-events", event);
    }
}
