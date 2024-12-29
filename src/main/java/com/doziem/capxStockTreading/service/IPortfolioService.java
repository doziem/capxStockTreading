package com.doziem.capxStockTreading.service;

import com.doziem.capxStockTreading.model.Portfolio;

import java.util.List;
import java.util.Optional;

public interface IPortfolioService {
    Portfolio createPortfolio(Long userId, Portfolio portfolio);

    List<Portfolio> getPortfoliosByUser(Long userId);

    Portfolio updatePortfolio(Long portfolioId, Portfolio updatedPortfolio);

    Optional<Portfolio> getPortfolio(Long portfolioId);

    void deletePortfolio(Long portfolioId);
}
