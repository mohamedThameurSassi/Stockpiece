package com.stockpiece.domain.repository;

import com.stockpiece.domain.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stockpiece.domain.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(UUID userId);
    List<Order> findByStockId(Integer stockId);
    List<Order> findByUserIdAndStatus(UUID userId, OrderStatus status);

    @Query("SELECT COALESCE(SUM(CASE WHEN o.orderType = 'MARKET_BUY' OR o.orderType = 'LIMIT_BUY' THEN o.quantity " +
           "WHEN o.orderType = 'MARKET_SELL' OR o.orderType = 'LIMIT_SELL' THEN -o.quantity ELSE 0 END), 0) " +
           "FROM Order o " +
           "WHERE o.stockId = :stockId " +
           "AND o.status = 'FILLED' " +
           "AND o.filledAt >= :since")
    Long getNetVolumeSince(@Param("stockId") Integer stockId, @Param("since") LocalDateTime since);
}