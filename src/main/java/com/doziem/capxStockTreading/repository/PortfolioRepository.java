package com.doziem.capxStockTreading.repository;

import com.doziem.capxStockTreading.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio,Long> {
    List<Portfolio> findByUserId(Long userId);

    Optional<Portfolio> findByName(String name);
}
