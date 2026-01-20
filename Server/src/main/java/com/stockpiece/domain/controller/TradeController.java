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
    public Order executeTrade(
        @RequestParam UUID userId,
        @RequestBody TradeRequest tradeRequest
    ){
        if ("LIMIT".equalsIgnoreCase(tradeRequest.getTradeType())){
            return tradeService.executeLimitOrder(userId, tradeRequest);
        }
        if ("MARKET_SELL".equalsIgnoreCase(tradeRequest.getTradeType())){
            return tradeService.executeMarketSellOrder(userId, tradeRequest);
        }
        if ("MARKET_BUY".equalsIgnoreCase(tradeRequest.getTradeType())){
            return tradeService.executeMarketBuyOrder(userId, tradeRequest);
        }
        return null;
    }

    @DeleteMapping("/{orderId}")
    public Order cancelOrder(
            @PathVariable Integer orderId,
            @RequestParam UUID userId
    ) {
        return tradeService.cancelOrder(userId, orderId);
    }
}