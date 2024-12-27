package com.doziem.capxStockTreading.repository;

import com.doziem.capxStockTreading.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio,Long> {
}
