package org.example.order_service.Service;

import org.example.order_service.Domain.model.MenuItemSnapshot;

public interface MenuItemSnapshotService {
    MenuItemSnapshot getMenuItemSnapshot(Long menuItemId);
}
