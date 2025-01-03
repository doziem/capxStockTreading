package com.doziem.capxStockTreading.service;

import com.doziem.capxStockTreading.dto.StockDto;
import com.doziem.capxStockTreading.model.Stock;

import java.util.List;

public interface IStockService {

    Stock createStock(Stock stock);

//    StockDto createStock(StockRequestDto request);

    List<Stock> getStocksByPortfolio(Long portfolioId);

    Stock updateStock(Long stockId, StockDto stock);

    List<StockDto> getAllStock();

    Stock getStockById(Long stockId);

    void deleteStock(Long stockId);

}
