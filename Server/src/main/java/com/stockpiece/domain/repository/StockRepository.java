package com.stockpiece.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stockpiece.domain.model.Stock;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
    Optional<Stock> findByTicker(String ticker);
}
