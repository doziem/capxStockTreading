package com.doziem.capxStockTreading.service;

import com.doziem.capxStockTreading.dto.PortfolioMetrics;
import com.doziem.capxStockTreading.dto.StockDto;
import com.doziem.capxStockTreading.model.Stock;

import java.util.List;

public interface IStockService {

    StockDto createStock(StockDto stock);

    StockDto createNewStock(StockDto stockDto);

    StockDto updateExistingStock(Long id, StockDto stockDto);

    List<StockDto> getAllStock();

    StockDto getStockById(Long stockId);

    void deleteStock(Long stockId);

    List<Stock> getRandomStocks();

    Double calculatePortfolioValue();

    PortfolioMetrics calculatePortfolioMetrics();

}
