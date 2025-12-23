package org.example.order_service.Consumer;

import lombok.RequiredArgsConstructor;
import org.example.MenuItemEvent;
import org.example.PaymentEvent;
import org.example.order_service.Domain.model.MenuItemSnapshot;
import org.example.order_service.Domain.model.ShoppingCart;
import org.example.order_service.Repository.MenuItemSnapshotRepository;
import org.example.order_service.Service.OrderService;
import org.example.order_service.Service.ShoppingCartService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final MenuItemSnapshotRepository menuItemSnapshotRepository;
    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;

    @KafkaListener(topics = "menu-item-events", groupId = "order-service-group")
    public void consumeMenuItemEvent(MenuItemEvent menuItemEvent) {
        System.out.println("Consume MenuItem Event: " + menuItemEvent);
        MenuItemSnapshot snapshot = menuItemSnapshotRepository
                .findById(menuItemEvent.getMenuItemId())
                .orElse(new MenuItemSnapshot());

        snapshot.setId(menuItemEvent.getMenuItemId());
        snapshot.setRestaurantId(menuItemEvent.getRestaurantId());
        snapshot.setName(menuItemEvent.getName());
        snapshot.setPrice(menuItemEvent.getPrice());
        snapshot.setCategory(menuItemEvent.getCategory());

        switch (menuItemEvent.getMenuItemEventType()) {
            case MENU_ITEM_CREATED -> snapshot.setActive(true);
            case MENU_ITEM_UPDATED -> {
                snapshot.setActive(snapshot.isActive());
            }
            case MENU_ITEM_DELETED -> snapshot.setActive(false);
        }

        menuItemSnapshotRepository.save(snapshot);
    }

    @KafkaListener(topics = "successful-payment", groupId = "order-service-group")
    public void consumeSuccessfulPayment(PaymentEvent paymentEvent) {
        orderService.create(paymentEvent.getAmount(),  paymentEvent.getUserId());
    }
}
