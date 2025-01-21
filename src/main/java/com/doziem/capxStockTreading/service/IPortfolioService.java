package com.doziem.capxStockTreading.service;

import com.doziem.capxStockTreading.dto.PortfolioDto;
import com.doziem.capxStockTreading.model.Portfolio;
import com.doziem.capxStockTreading.request.PortfolioRequestDto;

import java.util.List;
import java.util.Optional;

public interface IPortfolioService {
    Portfolio createPortfolio(PortfolioDto portfolio, Long userId );

    List<Portfolio> getPortfoliosByUser(Long userId);

    List<PortfolioDto> getAllPortfolio();

    Portfolio updatePortfolio(Long portfolioId, Portfolio updatedPortfolio);

    Optional<Portfolio> getPortfolio(Long portfolioId);

    void deletePortfolio(Long portfolioId);

//    Map<String, Object> calculatePortfolioMetrics();
}
