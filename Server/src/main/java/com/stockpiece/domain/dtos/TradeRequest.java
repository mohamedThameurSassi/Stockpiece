package com.stockpiece.domain.dtos;

import com.stockpiece.domain.enums.OrderType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TradeRequest {
    @NotBlank(message = "Stock symbol cannot be blank")
    private String stockSymbol;
    
    @NotNull(message = "Order type is required")
    private OrderType orderType;
    
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity; 
    
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price; // Only required for limit orders
}
