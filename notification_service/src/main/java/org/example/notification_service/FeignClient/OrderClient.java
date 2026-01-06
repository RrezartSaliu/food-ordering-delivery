package org.example.notification_service.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service", url = "http://order-service:8080")
public interface OrderClient {
    @GetMapping("/internal/order/{orderId}/get-user-id")
    Long getUserId(@PathVariable("orderId") Long orderId);
}