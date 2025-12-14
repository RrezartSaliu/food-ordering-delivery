package org.example.restaurant_service.Service;

import org.example.restaurant_service.Domain.model.Category;
import org.example.restaurant_service.Domain.model.MenuItem;

import java.math.BigDecimal;
import java.util.List;

public interface MenuItemService {
    MenuItem create (Long restaurantId, String name, BigDecimal price, Category category);
    MenuItem update (Long id, String name, BigDecimal price);
    MenuItem findById (Long id);
    MenuItem deleteById (Long id);
    List<MenuItem> findByCategory (Category category);
    List<MenuItem> findAll ();
}
