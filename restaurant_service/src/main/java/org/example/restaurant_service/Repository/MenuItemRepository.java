package org.example.restaurant_service.Repository;

import org.example.restaurant_service.Domain.model.Category;
import org.example.restaurant_service.Domain.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {
    List<MenuItem> findAllByCategory(Category category);
}
