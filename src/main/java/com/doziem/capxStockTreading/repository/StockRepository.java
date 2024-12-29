package com.doziem.capxStockTreading.repository;

import com.doziem.capxStockTreading.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock,Long> {
    List<Stock> findByPortfolioId(Long portfolioId);

}
