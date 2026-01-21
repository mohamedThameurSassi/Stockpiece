package com.stockpiece.domain.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.stockpiece.domain.service.TradeService;
import lombok.RequiredArgsConstructor;
import com.stockpiece.domain.model.*;
import com.stockpiece.domain.dtos.*;
import java.util.UUID;

@RestController
@RequestMapping("/trades")
@RequiredArgsConstructor
public class TradeController {
    
    private final TradeService tradeService;
    
    @PostMapping("/execute")
    public ResponseEntity<Order> executeTrade(
        @RequestParam UUID userId,
        @RequestBody TradeRequest tradeRequest
    ) {
        Order order = switch (tradeRequest.getOrderType()) {
            case MARKET_BUY -> tradeService.executeMarketBuyOrder(userId, tradeRequest);
            case MARKET_SELL -> tradeService.executeMarketSellOrder(userId, tradeRequest);
            case LIMIT_BUY, LIMIT_SELL -> tradeService.executeLimitOrder(userId, tradeRequest);
        };
        
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Order> cancelOrder(
            @PathVariable UUID orderId,
            @RequestParam UUID userId
    ) {
        return ResponseEntity.ok(tradeService.cancelOrder(userId, orderId));
    }
}