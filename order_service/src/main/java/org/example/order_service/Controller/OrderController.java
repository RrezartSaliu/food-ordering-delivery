package org.example.order_service.Controller;

import lombok.RequiredArgsConstructor;
import org.example.ApiResponse;
import org.example.order_service.Domain.DTO.OrderResponse;
import org.example.order_service.Domain.model.Order;
import org.example.order_service.Domain.model.OrderStatus;
import org.example.order_service.FeignClient.OrderClient;
import org.example.order_service.Mapper.OrderMapper;
import org.example.order_service.Service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/order/")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final OrderClient orderClient;

    @GetMapping("/userOrders")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> customerOrders(@RequestHeader("X-User-Id") String userId){
        List<Order> orders = orderService.findAllByUserId(Long.valueOf(userId));
        List<OrderResponse> orderResponses = new ArrayList<>();

        for(Order order : orders){
            orderResponses.add(orderMapper.toResponse(order));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "orders of user fetched", orderResponses));
    }

    @GetMapping("/get-order")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrder(@RequestHeader("X-User-Id") String userId, @RequestParam Long orderId){
        System.out.println("entering");
        Order order = orderService.findById(orderId);
        OrderResponse orderResponse = orderMapper.toResponse(order);
        return ResponseEntity.ok(new ApiResponse<>(true, "order retrieved", orderResponse));
    }

    @GetMapping("/restaurant-orders")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getRestaurantOrders(@RequestHeader("X-User-Id") String userId){
        List<Order> waitingOrders = orderService.findAllByRestaurantIdAndOrderStatus(Long.valueOf(userId), OrderStatus.WAITING);
        List<OrderResponse> orderResponses = new ArrayList<>();
        List<Order> deliveringOrders = orderService.findAllByRestaurantIdAndOrderStatus(Long.valueOf(userId), OrderStatus.DELIVERING);
        List<Order> deliveredOrders = orderService.findAllByRestaurantIdAndOrderStatus(Long.valueOf(userId), OrderStatus.DELIVERED);

        for(Order order : waitingOrders){
            orderResponses.add(orderMapper.toResponse(order));
        }
        for(Order order : deliveringOrders){
            orderResponses.add(orderMapper.toResponse(order));
        }
        for(Order order : deliveredOrders){
            orderResponses.add(orderMapper.toResponse(order));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "restaurant orders", orderResponses));
    }

    @GetMapping("/waiting-orders-restaurant")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getWaitingOrdersOfRestaurant(@RequestHeader("X-User-Id") String userId, @RequestParam Long restaurantId){
        if (orderClient.isWorker(String.valueOf(restaurantId), userId)){
            List<Order> waitingOrders = orderService.findAllByRestaurantIdAndOrderStatus(Long.valueOf(restaurantId), OrderStatus.WAITING);
            List<OrderResponse> orderResponses = new ArrayList<>();
            for(Order order : waitingOrders){
                orderResponses.add(orderMapper.toResponse(order));
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Waiting orders of restaurant", orderResponses));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Driver not in restaurant", null));
    }

}