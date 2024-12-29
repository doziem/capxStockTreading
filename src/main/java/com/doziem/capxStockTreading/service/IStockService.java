package com.doziem.capxStockTreading.service;

import com.doziem.capxStockTreading.model.Stock;

import java.util.List;

public interface IStockService {

    Stock createStock(Long portfolioId, Stock stock);

    List<Stock> getStocksByPortfolio(Long portfolioId);

    Stock updateStock(Long stockId, Stock stock);

    List<Stock> getAllStock();

    void deleteStock(Long stockId);

}
