package com.doziem.capxStockTreading.service;

import com.doziem.capxStockTreading.dto.StockDto;
import com.doziem.capxStockTreading.exception.ResourceNotFoundException;
import com.doziem.capxStockTreading.model.Portfolio;
import com.doziem.capxStockTreading.model.Stock;
import com.doziem.capxStockTreading.repository.PortfolioRepository;
import com.doziem.capxStockTreading.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StockServiceImpl implements IStockService{

    @Autowired
    public StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Autowired
    public PortfolioRepository portfolioRepository;



    @Override
    @Transactional
    public Stock createStock(Stock stock) {
        Optional<Portfolio> optionalPortfolio = Optional.empty();
        // Validate input
        if (stock == null) {
            throw new IllegalArgumentException("Stock object cannot be null");
        }

        // Validate Portfolio and its name
        if (stock.getPortfolio() == null || stock.getPortfolio().getName() == null || stock.getPortfolio().getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Portfolio name cannot be null or empty");
        }

        String portfolioName = stock.getPortfolio().getName().trim();

        // Find the portfolio or create a new one if it doesn't exist
        Portfolio portfolio = Optional.ofNullable(portfolioRepository.findByName(portfolioName))
                .orElseGet(() -> {
                    Portfolio newPortfolio = new Portfolio();
                    newPortfolio.setName(portfolioName);
                    return portfolioRepository.save(newPortfolio); // Save the new portfolio and return
                });

        // Associate the found or created portfolio with the stock
        stock.setPortfolio(portfolio);
        // Save and return the stock
        return stockRepository.save(stock);
    }


    public List<Stock> getStocksByPortfolio(Long portfolioId) {
        return stockRepository.findByPortfolioId(portfolioId);
    }

    @Override
    public Stock updateStock(Long stockId, StockDto updatedStock) {

        return stockRepository.findById(stockId)
                .map(exixtingStock->updateExistingStock(exixtingStock, updatedStock))
                .map(stockRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
    }

    private Stock updateExistingStock(Stock existingStock, StockDto request) {

        existingStock.setName(request.getName() != null ? request.getName() : existingStock.getName());
        existingStock.setBuyPrice(request.getBuyPrice() != null ? request.getBuyPrice() : existingStock.getBuyPrice());

        existingStock.setTicker(request.getTicker() != null ? request.getTicker() : existingStock.getTicker());
        existingStock.setQuantity(request.getQuantity() != null ? request.getQuantity() : existingStock.getQuantity());

        existingStock.setVolume(request.getVolume() != null ? request.getVolume() : existingStock.getVolume());

        if (request.getPortfolio() != null) {
            Portfolio newPortfolio = new Portfolio();
            newPortfolio.setName(request.getPortfolio().getName());

            // Save the new portfolio using the repository (if required)
            newPortfolio = portfolioRepository.save(newPortfolio);

            existingStock.setPortfolio(newPortfolio);
        }

        return  existingStock;
    }


    @Override
    public List<StockDto> getAllStock() {
        List<Stock> stocks = stockRepository.findAll();
        return stocks.stream()
                .map(StockDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Stock getStockById(Long stockId) {

        return  stockRepository.findById(stockId)
                .orElseThrow(()->new ResourceNotFoundException("Stock not found"));
    }


    @Override
    public void deleteStock(Long stockId) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
        stockRepository.delete(stock);
    }
}
