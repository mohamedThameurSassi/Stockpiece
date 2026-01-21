package com.stockpiece.domain.controller;

import com.stockpiece.domain.model.Portfolio;
import com.stockpiece.domain.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/portfolios")
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService portfolioService;
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Portfolio>> getUserPortfolio(@PathVariable UUID userId) {
        return ResponseEntity.ok(portfolioService.getUserPortfolio(userId));
    }
    
    @GetMapping("/user/{userId}/stock/{stockId}")
    public ResponseEntity<Portfolio> getUserStockPortfolio(
            @PathVariable UUID userId,
            @PathVariable Integer stockId
    ) {
        return ResponseEntity.ok(portfolioService.getPortfolioByUserAndStock(userId, stockId));
    }
}
