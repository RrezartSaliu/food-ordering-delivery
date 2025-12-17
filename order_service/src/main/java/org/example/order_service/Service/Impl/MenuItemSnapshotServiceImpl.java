package org.example.order_service.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.order_service.Domain.model.MenuItemSnapshot;
import org.example.order_service.Exception.ResourceNotFoundException;
import org.example.order_service.Repository.MenuItemSnapshotRepository;
import org.example.order_service.Service.MenuItemSnapshotService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuItemSnapshotServiceImpl implements MenuItemSnapshotService {
    private final MenuItemSnapshotRepository menuItemSnapshotRepository;

    @Override
    public MenuItemSnapshot getMenuItemSnapshot(Long menuItemId) {
        return menuItemSnapshotRepository.findById(menuItemId).orElseThrow(()-> new ResourceNotFoundException("MenuItemSnapshot not found with id " + menuItemId));
    }
}
