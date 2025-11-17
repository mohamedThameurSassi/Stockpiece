package com.stockpiece.domain.stock;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stocks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String characterName;
    
    @Column(nullable = false, unique = true)
    private String ticker;
    
    @Column(nullable = false)
    private Double bountyPercentage;
    
    @Column(nullable = false)
    private Double currentPrice;
    
    private Double previousPrice;
    
    private Double ma50;
    private Double ma200;
    private Double rsi;
    
    @Column(name = "volume_24h")
    private Integer volume24h = 0;
    
    private String imageUrl;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
