package com.doziem.capxStockTreading.repository;

import com.doziem.capxStockTreading.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock,Long> {
}
