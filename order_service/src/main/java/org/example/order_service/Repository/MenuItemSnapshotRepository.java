package org.example.order_service.Repository;

import org.example.order_service.Domain.model.MenuItemSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemSnapshotRepository extends JpaRepository<MenuItemSnapshot, Long> {
}
