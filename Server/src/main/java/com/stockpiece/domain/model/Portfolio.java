package com.stockpiece.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "portfolios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "average_buy_price", nullable = false)
    private Double averageBuyPrice;

    @Transient
    private Double gains;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PostLoad
    @PostPersist
    @PostUpdate
    public void calculateGains() {
        if (stock != null && stock.getCurrentPrice() != null) {
            this.gains = (stock.getCurrentPrice() - averageBuyPrice) * quantity;
        }
    }

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
