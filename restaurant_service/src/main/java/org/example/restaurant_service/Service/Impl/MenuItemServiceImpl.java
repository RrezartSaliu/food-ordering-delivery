package org.example.restaurant_service.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.restaurant_service.Domain.model.Category;
import org.example.restaurant_service.Domain.model.MenuItem;
import org.example.restaurant_service.Repository.MenuItemRepository;
import org.example.restaurant_service.Service.MenuItemService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;

    @Override
    public MenuItem create(Long restaurantId, String name, BigDecimal price, Category category) {
        MenuItem menuItem = new MenuItem();
        menuItem.setRestaurantId(restaurantId);
        menuItem.setName(name);
        menuItem.setPrice(price);
        menuItem.setCategory(category);
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem update(Long id, String name, BigDecimal price) {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(id);
        menuItem.setName(name);
        menuItem.setPrice(price);
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem findById(Long id) {
        return menuItemRepository.findById(id).orElse(null);
    }

    @Override
    public MenuItem deleteById(Long id) {
        MenuItem removedItem = this.findById(id);
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
}
