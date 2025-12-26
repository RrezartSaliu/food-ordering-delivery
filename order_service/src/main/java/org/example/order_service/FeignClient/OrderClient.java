package org.example.order_service.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service", url = "http://auth-service:8080")
public interface OrderClient {
    @GetMapping("/auth/{restaurantId}/workers/{userId}/exists")
    boolean isWorker(@PathVariable String restaurantId, @PathVariable String userId);
}
