package com.stockpiece.domain.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
@Data
public class TradeRequest {
    @NotBlank(message = "Stock symbol cannot be blank")
    private String stockSymbol;

    @NotBlank(message = "Action cannot be blank")
    @Pattern(regexp = "BUY|SELL", message = "Action must be BUY or SELL")
    private String action;

    @NotBlank(message = "Trade type cannot be blank")
    @Pattern(regexp = "MARKET|LIMIT", message = "Trade type must be MARKET or LIMIT")
    private String tradeType;
    
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity; 
    
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price;
}
