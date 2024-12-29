package com.doziem.capxStockTreading.repository;

import com.doziem.capxStockTreading.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio,Long> {
    List<Portfolio> findByUserId(Long userId);
}
