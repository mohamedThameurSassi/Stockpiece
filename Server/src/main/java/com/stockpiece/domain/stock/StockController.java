package com.stockpiece.domain.stock;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;
    
    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        return ResponseEntity.ok(stockService.getAllStocks());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Integer id) {
        return ResponseEntity.ok(stockService.getStockById(id));
    }
    
    @GetMapping("/ticker/{ticker}")
    public ResponseEntity<Stock> getStockByTicker(@PathVariable String ticker) {
        return ResponseEntity.ok(stockService.getStockByTicker(ticker));
    }
    
    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody CreateStockRequest request) {
        Stock stock = stockService.createStock(
                request.getCharacterName(),
                request.getTicker(),
                request.getBountyPercentage(),
                request.getCurrentPrice()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(stock);
    }
}

@Data
class CreateStockRequest {
    private String characterName;
    private String ticker;
    private Double bountyPercentage;
    private Double currentPrice;
}
