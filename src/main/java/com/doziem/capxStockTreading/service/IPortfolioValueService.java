package com.doziem.capxStockTreading.service;

import com.doziem.capxStockTreading.model.Portfolio;

public interface IPortfolioValueService {

//    Portfolio assignStocksToUser(Long userId);

    Double calculatePortfolioValue(Long portfolioId);
}
