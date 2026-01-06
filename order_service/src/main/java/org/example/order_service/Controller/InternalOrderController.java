package org.example.order_service.Controller;

import lombok.RequiredArgsConstructor;
import org.example.order_service.Service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/order")
@RequiredArgsConstructor
public class InternalOrderController {
    private final OrderService orderService;

    @GetMapping("{orderId}/get-user-id")
    Long getUserId(@PathVariable("orderId") String orderId){
        return orderService.findById(Long.valueOf(orderId)).getUserId();
    }
}
