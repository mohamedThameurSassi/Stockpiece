package com.stockpiece.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stockpiece.domain.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(UUID userId);
    List<Order> findByStockId(Integer stockId);
    List<Order> findByUserIdAndStatus(UUID userId, String status);

    @Query("SELECT COALESCE(SUM(CASE WHEN o.orderType LIKE '%BUY' THEN o.quantity " +
           "WHEN o.orderType LIKE '%SELL' THEN -o.quantity ELSE 0 END), 0) " +
           "FROM Order o " +
           "WHERE o.stockId = :stockId " +
           "AND o.status = 'FILLED' " +
           "AND o.filledAt >= :since")
    Long getNetVolumeSince(@Param("stockId") Integer stockId, @Param("since") LocalDateTime since);
}