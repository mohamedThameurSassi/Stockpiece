package com.stockpiece.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    
    public Order placeOrder(Integer userId, Integer stockId, Integer quantity, String orderType, Double pricePerShare) {
        Order order = Order.builder()
                .userId(userId)
                .stockId(stockId)
                .quantity(quantity)
                .orderType(orderType)
                .pricePerShare(pricePerShare)
                .status("PENDING")
                .build();
        return orderRepository.save(order);
    }
    
    public List<Order> getUserOrders(Integer userId) {
        return orderRepository.findByUserId(userId);
    }
    
    public List<Order> getPendingUserOrders(Integer userId) {
        return orderRepository.findByUserIdAndStatus(userId, "PENDING");
    }
    
    public List<Order> getStockOrders(Integer stockId) {
        return orderRepository.findByStockId(stockId);
    }
    
    public Order fillOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus("FILLED");
        order.setFilledAt(LocalDateTime.now());
        return orderRepository.save(order);
    }
    
    public Order cancelOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus("CANCELLED");
        return orderRepository.save(order);
    }
}
