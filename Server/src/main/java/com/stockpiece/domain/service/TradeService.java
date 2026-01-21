package com.stockpiece.domain.service;

import org.springframework.stereotype.Service;
import com.stockpiece.domain.model.Stock;
import com.stockpiece.domain.model.Portfolio;
import com.stockpiece.domain.repository.*;
import com.stockpiece.domain.model.Order;
import com.stockpiece.domain.dtos.TradeRequest;
import com.stockpiece.domain.model.User;
import com.stockpiece.domain.enums.OrderStatus;
import com.stockpiece.domain.enums.OrderType;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final UserRepository userRepository;
    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    private final PortfolioRepository portfolioRepository;

   @Transactional
   public Order executeMarketBuyOrder(UUID userId, TradeRequest tradeRequest) {
        Stock stock = stockRepository.findByTicker(tradeRequest.getStockSymbol())
                .orElseThrow(() -> new RuntimeException("Stock not found"));
        Double totalPrice = stock.getCurrentPrice() * tradeRequest.getQuantity();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (user.getBerryBalance() < totalPrice) {
            throw new RuntimeException("Insufficient balance");
        }
        

        user.setBerryBalance(user.getBerryBalance() - totalPrice);
        userRepository.save(user);

        Portfolio portfolio = portfolioRepository.findByUserIdAndStock_Id(userId, stock.getId())
                .orElse(Portfolio.builder()
                        .userId(userId)
                        .stock(stock)
                        .quantity(0)
                        .averageBuyPrice(0.0)
                        .build());

        double totalCost = (portfolio.getQuantity() * portfolio.getAverageBuyPrice()) + totalPrice;
        int newQuantity = portfolio.getQuantity() + tradeRequest.getQuantity();
        
        portfolio.setAverageBuyPrice(totalCost / newQuantity);
        portfolio.setQuantity(newQuantity);
        portfolioRepository.save(portfolio);
        Order order = Order.builder()
                .userId(userId)
                .stockId(stock.getId())
                .quantity(tradeRequest.getQuantity())
                .orderType(OrderType.MARKET_BUY)
                .pricePerShare(stock.getCurrentPrice())
                .status(OrderStatus.FILLED)
                .filledAt(LocalDateTime.now())
                .build();
        return orderRepository.save(order);
    }

    @Transactional
    public Order executeMarketSellOrder(UUID userId, TradeRequest tradeRequest){
        Stock stock = stockRepository.findByTicker(tradeRequest.getStockSymbol())
                .orElseThrow(() -> new RuntimeException("Stock not found"));
        
        // 1. Check Portfolio (Has Stock?)
        Portfolio portfolio = portfolioRepository.findByUserIdAndStock_Id(userId, stock.getId())
                .orElseThrow(() -> new RuntimeException("You do not own this stock"));

        if (portfolio.getQuantity() < tradeRequest.getQuantity()) {
            throw new RuntimeException("Insufficient stocks to sell");
        }

        // 2. Add Balance
        Double totalProceeds = stock.getCurrentPrice() * tradeRequest.getQuantity();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setBerryBalance(user.getBerryBalance() + totalProceeds);
        userRepository.save(user);

        // 3. Update Portfolio (Remove Stock)
        portfolio.setQuantity(portfolio.getQuantity() - tradeRequest.getQuantity());
        if (portfolio.getQuantity() == 0) {
            portfolioRepository.delete(portfolio);
        } else {
            portfolioRepository.save(portfolio);
        }

        Order order = Order.builder()
                .userId(userId)
                .stockId(stock.getId())
                .quantity(tradeRequest.getQuantity())
                .orderType(OrderType.MARKET_SELL)
                .pricePerShare(stock.getCurrentPrice())
                .status(OrderStatus.FILLED)
                .filledAt(LocalDateTime.now())
                .build();
        return orderRepository.save(order);
    }
    
    @Transactional
    public Order executeLimitOrder(UUID userId, TradeRequest tradeRequest) {
        Stock stock = stockRepository.findByTicker(tradeRequest.getStockSymbol())
                .orElseThrow(() -> new RuntimeException("Stock not found"));
        
        if (tradeRequest.getPrice() == null) {
            throw new RuntimeException("Price is required for limit orders");
        }
        
        Order order = Order.builder()
                .userId(userId)
                .stockId(stock.getId())
                .quantity(tradeRequest.getQuantity())
                .orderType(tradeRequest.getOrderType())
                .pricePerShare(tradeRequest.getPrice())
                .status(OrderStatus.PENDING)
                .build();
        return orderRepository.save(order);
    }

    @Transactional
    public Order cancelOrder(UUID userId, UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("You are not authorized to cancel this order");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
             throw new RuntimeException("Order cannot be cancelled in status: " + order.getStatus());
        }
        
        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
}