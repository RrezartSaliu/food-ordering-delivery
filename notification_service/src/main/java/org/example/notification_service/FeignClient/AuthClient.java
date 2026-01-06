package org.example.notification_service.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "auth-service", url = "http://auth-service:8080")
public interface AuthClient {
    @GetMapping("/auth/drivers/{restaurantId}")
    List<Long> driversIds(@PathVariable String restaurantId);
}
