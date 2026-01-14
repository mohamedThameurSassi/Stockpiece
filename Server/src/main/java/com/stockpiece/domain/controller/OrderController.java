package com.stockpiece.domain.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stockpiece.domain.model.Order;
import com.stockpiece.domain.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody PlaceOrderRequest request) {
        Order order = orderService.placeOrder(
                request.getUserId(),
                request.getStockId(),
                request.getQuantity(),
                request.getOrderType(),
                request.getPricePerShare()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Integer userId) {
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }
    
    @GetMapping("/user/{userId}/pending")
    public ResponseEntity<List<Order>> getPendingUserOrders(@PathVariable Integer userId) {
        return ResponseEntity.ok(orderService.getPendingUserOrders(userId));
    }
    
    @GetMapping("/stock/{stockId}")
    public ResponseEntity<List<Order>> getStockOrders(@PathVariable Integer stockId) {
        return ResponseEntity.ok(orderService.getStockOrders(stockId));
    }
    
    @PutMapping("/{id}/fill")
    public ResponseEntity<Order> fillOrder(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.fillOrder(id));
    }
    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }
}

@Data
class PlaceOrderRequest {
    private Integer userId;
    private Integer stockId;
    private Integer quantity;
    private String orderType;
    private Double pricePerShare;
}
