package com.stockpiece.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

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
    private UUID userId;
    
    @Column(name = "stock_id", nullable = false)
    private Integer stockId;
    
    @Column(nullable = false)
    private String orderType; // "BUY" or "SELL"
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false)
    private Double pricePerShare;
    
    @Builder.Default
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
