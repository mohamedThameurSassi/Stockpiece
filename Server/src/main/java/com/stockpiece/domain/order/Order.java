package com.stockpiece.domain.order;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    
    @Column(name = "stock_id", nullable = false)
    private Integer stockId;
    
    @Column(nullable = false)
    private String orderType; // "BUY" or "SELL"
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false)
    private Double pricePerShare;
    
    @Column(nullable = false)
    private String status = "PENDING"; // "PENDING", "FILLED", "CANCELLED"
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "filled_at")
    private LocalDateTime filledAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
