package com.stockpiece.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.stockpiece.domain.model.Stock;
import com.stockpiece.domain.repository.OrderRepository;
import com.stockpiece.domain.repository.PortfolioRepository;
import com.stockpiece.domain.repository.StockRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketSimulationService {
    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    private final PortfolioRepository portfolioRepository;

    @Scheduled(fixedRate = 300000)
    public void simulateMarketMovement(){
        List<Stock> stocks = stockRepository.findAll();
        LocalDateTime fiveMinAgo = LocalDateTime.now().minusMinutes(5);

        for(Stock stock : stocks){
            double alpha = 0.0001;
            double beta = 0.0005;  
            
            Long currentSupplyVal = portfolioRepository.sumQuantityByStockId(stock.getId());
            Long currentSupply = currentSupplyVal != null ? currentSupplyVal : 0L;
            Long netVolumeVal = orderRepository.getNetVolumeSince(stock.getId(), fiveMinAgo);
            Long latestVolume = netVolumeVal != null ? netVolumeVal : 0L;
            Long oldSupplyVal = stock.getCirculatingSupply();
            Long oldSupply = oldSupplyVal != null ? oldSupplyVal : 0L;
            double deltaSupply = (double) (currentSupply - oldSupply);

            double price = stock.getCurrentPrice();
            
            price += (alpha * deltaSupply) + (beta * latestVolume);
            
            if (price < 0.1) {
                price = 0.1;
            }

            // 5. Save Updates
            stock.setCirculatingSupply(currentSupply);
            stock.setCurrentPrice(price);
            
            stockRepository.save(stock);
        }
    }
}