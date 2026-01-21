package com.stockpiece.domain.service;

import com.stockpiece.domain.model.Portfolio;
import com.stockpiece.domain.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    
    public List<Portfolio> getUserPortfolio(UUID userId) {
        return portfolioRepository.findByUserId(userId);
    }
    
    public Portfolio getPortfolioByUserAndStock(UUID userId, Integer stockId) {
        return portfolioRepository.findByUserIdAndStock_Id(userId, stockId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
    }
}
