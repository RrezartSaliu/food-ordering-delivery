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
        Order order = orderService.findById(orderId);
        Long uid = Long.valueOf(userId);

        if (
                !uid.equals(order.getDriverId()) &&
                        !uid.equals(order.getUserId()) &&
                        !uid.equals(order.getRestaurantId())
        ) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse<>(false, "invalid order", null));
        }

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
            List<Order> waitingOrders = orderService.findAllByRestaurantIdAndOrderStatus(restaurantId, OrderStatus.WAITING);
            List<OrderResponse> orderResponses = new ArrayList<>();
            for(Order order : waitingOrders){
                orderResponses.add(orderMapper.toResponse(order));
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Waiting orders of restaurant", orderResponses));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Driver not in restaurant", null));
    }

    @PostMapping("deliver-order")
    public ResponseEntity<ApiResponse<OrderResponse>> deliverOrder (@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String role, @RequestParam Long orderId, @RequestParam Long restaurantId){
        if(orderClient.isWorker(String.valueOf(restaurantId), userId) & role.equals("ROLE_DRIVER")) {
            Order order = orderService.deliver(orderId, Long.valueOf(userId));
            OrderResponse orderResponse = orderMapper.toResponse(order);
            return ResponseEntity.ok(new ApiResponse<>(true, "order delivering", orderResponse));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Wrong driver or not driver", null));
    }

    @GetMapping("/driver-cart-count")
    public ResponseEntity<ApiResponse<Integer>> driverCartCount (@RequestHeader("X-User-Id") String userId){
        Integer cartCount = orderService.findAllByDriverIdAndOrderStatus(Long.valueOf(userId), OrderStatus.DELIVERING).size();
        return ResponseEntity.ok(new ApiResponse<>(true, "orders of cart count", cartCount));
    }

    @GetMapping("/driver-cart")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> driverCart(@RequestHeader("X-User-Id") String userId){
        List<Order> orders = orderService.findAllByDriverIdAndOrderStatus(Long.valueOf(userId), OrderStatus.DELIVERING);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for(Order order : orders){
            orderResponses.add(orderMapper.toResponse(order));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Driver cart orders", orderResponses));
    }

    @PostMapping("/order-delivered")
    public ResponseEntity<ApiResponse<OrderResponse>> orderDelivered(@RequestHeader("X-User-Id") String userId, @RequestParam Long orderId, @RequestParam Long restaurantId){
        if (orderClient.isWorker(String.valueOf(restaurantId), userId)) {
            Order order = orderService.delivered(orderId);
            OrderResponse orderResponse = orderMapper.toResponse(order);
            return  ResponseEntity.ok(new ApiResponse<>(true, "order delivered", orderResponse));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Wrong driver or not driver", null));
    }
}