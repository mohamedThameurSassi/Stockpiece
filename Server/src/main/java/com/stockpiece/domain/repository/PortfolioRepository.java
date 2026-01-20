package com.stockpiece.domain.repository;

import com.stockpiece.domain.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
    Optional<Portfolio> findByUserIdAndStock_Id(UUID userId, Integer stockId);
    List<Portfolio> findByUserId(UUID userId);

    @Query("SELECT COALESCE(SUM(p.quantity), 0) FROM Portfolio p WHERE p.stock.id = :stockId")
    Long sumQuantityByStockId(@Param("stockId") Integer stockId);
}
