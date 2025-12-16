package org.example.restaurant_service.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.restaurant_service.Domain.model.Category;
import org.example.restaurant_service.Domain.model.MenuItem;
import org.example.restaurant_service.Exception.ForbiddenAction;
import org.example.restaurant_service.Exception.ResourceNotFound;
import org.example.restaurant_service.Repository.MenuItemRepository;
import org.example.restaurant_service.Service.MenuItemService;
import org.example.restaurant_service.Service.Producer.MenuItemEventProducer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final MenuItemEventProducer menuItemEventProducer;

    @Override
    public MenuItem create(Long restaurantId, String name, BigDecimal price, Category category) {
        MenuItem menuItem = new MenuItem();
        menuItem.setRestaurantId(restaurantId);
        menuItem.setName(name);
        menuItem.setPrice(price);
        menuItem.setCategory(category);

        menuItem =  menuItemRepository.save(menuItem);

        menuItemEventProducer.createMenuItem(menuItem);
        return menuItem;
    }

    @Override
    public MenuItem update(Long id, String name, BigDecimal price, Category category, Long userId) {
        MenuItem menuItem = this.findById(id);
        if  (menuItem == null) {
            throw new ResourceNotFound("Item not found");
        }
        if (!menuItem.getRestaurantId().equals(userId)) {
            throw new ForbiddenAction("Not allowed to update item");
        }
        menuItem.setCategory(category);
        menuItem.setName(name);
        menuItem.setPrice(price);
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem findById(Long id) {
        return menuItemRepository.findById(id).orElse(null);
    }

    @Override
    public MenuItem deleteById(Long id,  Long userId) {
        MenuItem removedItem = this.findById(id);
        if (removedItem == null) {
            throw new ResourceNotFound("Item not found");
        }
        if(!removedItem.getRestaurantId().equals(userId)) {
            throw new ForbiddenAction("Not allowed to delete item");
        }
        menuItemRepository.delete(removedItem);

        return removedItem;
    }

    @Override
    public List<MenuItem> findByCategory(Category category) {
        return menuItemRepository.findAllByCategory(category);
    }

    @Override
    public List<MenuItem> findAll() {
        return menuItemRepository.findAll();
    }

    @Override
    public List<MenuItem> findByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<MenuItem> findMyRestaurantItems(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }
}
