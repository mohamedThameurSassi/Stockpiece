package com.stockpiece.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stockpiece.domain.model.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Integer userId);
    List<Order> findByStockId(Integer stockId);
    List<Order> findByUserIdAndStatus(Integer userId, String status);
}
