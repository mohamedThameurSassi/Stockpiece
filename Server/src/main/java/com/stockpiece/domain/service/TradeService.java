package com.stockpiece.domain.service;

import org.springframework.stereotype.Service;
import com.stockpiece.domain.model.Stock;
import com.stockpiece.domain.repository.*;
import com.stockpiece.domain.model.Order;
import com.stockpiece.domain.dtos.TradeRequest;
import com.stockpiece.domain.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final UserRepository userRepository;
    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    //private final PortfolioService portfolioService;

   @Transactional
   public Order executeMarketBuyOrder(Integer userId, TradeRequest tradeRequest) {
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

        //todo update portfolio here

        Order order = Order.builder()
                .userId(userId)
                .stockId(stock.getId())
                .quantity(tradeRequest.getQuantity())
                .orderType("MARKET_BUY")
                .pricePerShare(stock.getCurrentPrice())
                .status("FILLED")
                .build();
        return orderRepository.save(order);
    }

    @Transactional
    public Order executeMarketSellOrder(Integer userId, TradeRequest tradeRequest){
        Stock stock = stockRepository.findByTicker(tradeRequest.getStockSymbol())
                .orElseThrow(() -> new RuntimeException("Stock not found"));
       // availableStocks = portfolioService.getAvailableStocks(userId, stock.getId());
        //if (availableStocks < tradeRequest.getQuantity()) {
        //    throw new RuntimeException("Insufficient stocks to sell");
        //}
        Double totalProceeds = stock.getCurrentPrice() * tradeRequest.getQuantity();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setBerryBalance(user.getBerryBalance() + totalProceeds);
        userRepository.save(user);

        //todo update portfolio here

        Order order = Order.builder()
                .userId(userId)
                .stockId(stock.getId())
                .quantity(tradeRequest.getQuantity())
                .orderType("MARKET_SELL")
                .pricePerShare(stock.getCurrentPrice())
                .status("FILLED")
                .build();
        return orderRepository.save(order);
    }
    @Transactional
    public Order executeLimitOrder(Integer userId, TradeRequest tradeRequest) {
        Stock stock = stockRepository.findByTicker(tradeRequest.getStockSymbol())
                .orElseThrow(() -> new RuntimeException("Stock not found"));
        
        Order order = Order.builder()
                .userId(userId)
                .stockId(stock.getId())
                .quantity(tradeRequest.getQuantity())
                .orderType(tradeRequest.getTradeType())
                .pricePerShare(tradeRequest.getPrice())
                .status("PENDING")
                .build();
        return orderRepository.save(order);
    }

@Transactional
    public Order cancelOrder(Integer userId, Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        // SECURITY CHECK: Does this order belong to the user?
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("You are not authorized to cancel this order");
        }

        if (!order.getStatus().equals("PENDING")) {
            throw new RuntimeException("Only pending orders can be cancelled");
        }
        
        order.setStatus("CANCELLED");
        
        return orderRepository.save(order);
    }
}