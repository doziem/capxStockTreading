package com.doziem.capxStockTreading.service;

import com.doziem.capxStockTreading.exception.ResourceNotFoundException;
import com.doziem.capxStockTreading.model.Portfolio;
import com.doziem.capxStockTreading.model.Stock;
import com.doziem.capxStockTreading.repository.PortfolioRepository;
import com.doziem.capxStockTreading.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StockServiceImpl implements IStockService{

    @Autowired
    public StockRepository stockRepository;

    @Autowired
    public PortfolioRepository portfolioRepository;

    @Override
    public Stock createStock(Long portfolioId, Stock stock) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));

        stock.setPortfolio(portfolio);
        return stockRepository.save(stock);
    }

    public List<Stock> getStocksByPortfolio(Long portfolioId) {
        return stockRepository.findByPortfolioId(portfolioId);
    }

    @Override
    public Stock updateStock(Long stockId, Stock updatedStock) {
        Stock existingStock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));

        existingStock.setTicker(updatedStock.getTicker());
        existingStock.setName(updatedStock.getName());
        existingStock.setQuantity(updatedStock.getQuantity());
        existingStock.setBuyPrice(updatedStock.getBuyPrice());
        existingStock.setVolume(updatedStock.getVolume());

        return stockRepository.save(existingStock);
    }

    @Override
    public List<Stock> getAllStock() {
        return stockRepository.findAll();
    }

    @Override
    public void deleteStock(Long stockId) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
        stockRepository.delete(stock);
    }
}
