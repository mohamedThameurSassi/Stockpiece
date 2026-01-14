package com.stockpiece.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.stockpiece.domain.model.Stock;
import com.stockpiece.domain.repository.StockRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }
    
    public Stock getStockById(Integer id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found"));
    }
    
    public Stock getStockByTicker(String ticker) {
        return stockRepository.findByTicker(ticker)
                .orElseThrow(() -> new RuntimeException("Stock not found"));
    }
    
    public Stock updatePrice(Integer stockId, Double newPrice) {
        Stock stock = getStockById(stockId);
        stock.setPreviousPrice(stock.getCurrentPrice());
        stock.setCurrentPrice(newPrice);
        return stockRepository.save(stock);
    }
    
    public Stock createStock(String characterName, String ticker, Double bountyPercentage, Double currentPrice) {
        Stock stock = Stock.builder()
                .characterName(characterName)
                .ticker(ticker)
                .bountyPercentage(bountyPercentage)
                .currentPrice(currentPrice)
                .previousPrice(currentPrice)
                .build();
        return stockRepository.save(stock);
    }
}
