package com.doziem.capxStockTreading.service;

import com.doziem.capxStockTreading.dto.PortfolioDto;
import com.doziem.capxStockTreading.dto.UserDto;
import com.doziem.capxStockTreading.exception.ResourceNotFoundException;
import com.doziem.capxStockTreading.model.Portfolio;
import com.doziem.capxStockTreading.model.Stock;
import com.doziem.capxStockTreading.model.User;
import com.doziem.capxStockTreading.repository.PortfolioRepository;
import com.doziem.capxStockTreading.repository.UserRepository;
import com.doziem.capxStockTreading.request.PortfolioRequestDto;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PortfolioServiceImpl implements IPortfolioService{

    @Autowired
    public PortfolioRepository portfolioRepository;

    @Autowired
    public UserRepository userRepository;

    public IStockService stockService;

    @Autowired
    public PortfolioServiceImpl(IStockService stockService) {
        this.stockService = stockService;
    }

    @Override
    public Portfolio createPortfolio(PortfolioDto portfolioDto, Long userId) {

        // Validate the Portfolio name
        if (portfolioDto.getName() == null || portfolioDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Portfolio name cannot be null or empty");
        }

        // Find the user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Create a new Portfolio entity from the DTO
        Portfolio portfolio = new Portfolio();
        portfolio.setName(portfolioDto.getName());
        portfolio.setUser(user); // Set the User entity

        // Save the Portfolio entity
        return portfolioRepository.save(portfolio);
    }

    @Override
    public List<Portfolio> getPortfoliosByUser(Long userId) {
        return portfolioRepository.findByUserId(userId);
    }

    @Override
    public List<PortfolioDto> getAllPortfolio() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        return portfolios.stream()
                .map(PortfolioDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Portfolio updatePortfolio(Long portfolioId, Portfolio updatedPortfolio) {
        Portfolio existingPortfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));

        existingPortfolio.setName(updatedPortfolio.getName());
        return portfolioRepository.save(existingPortfolio);
    }

    @Override
    public Optional<Portfolio> getPortfolio(Long portfolioId) {
        return Optional.ofNullable(portfolioRepository.findById(portfolioId).orElseThrow(() -> new ResourceNotFoundException("Portfolio not found")));
    }

    @Override
    public void deletePortfolio(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));
        portfolioRepository.delete(portfolio);

    }

    public List<Stock> getPortfolio() {
        return stockService.getRandomStocks();
    }

//    @Override
//    public Map<String, Object> calculatePortfolioMetrics() {
//        List<Stock> stocks = getPortfolio();
//
//        // Total Portfolio Value
//        double totalValue = stocks.stream()
//                .mapToDouble(Stock::getBuyPrice)
//                .sum();
//
//        // Average Stock Price
//        double averagePrice = totalValue / stocks.size();
//
//        // Highest Priced Stock
//        Stock highestPricedStock = stocks.stream()
//                .max(Comparator.comparingDouble(Stock::getBuyPrice))
//                .orElse(null);
//
//        // Lowest Priced Stock
//        Stock lowestPricedStock = stocks.stream()
//                .min(Comparator.comparingDouble(Stock::getBuyPrice))
//                .orElse(null);
//
//        // Price Distribution
//        Map<String, Double> priceDistribution = stocks.stream()
//                .collect(Collectors.toMap(
//                        Stock::getTicker,
//                        stock -> (stock.getBuyPrice() / totalValue) * 100
//                ));
//
//        // Return all metrics in a map
//        assert highestPricedStock != null;
//        assert lowestPricedStock != null;
//        return Map.of(
//                "totalValue", totalValue,
//                "averagePrice", averagePrice,
//                "highestPricedStock", highestPricedStock,
//                "lowestPricedStock", lowestPricedStock,
//                "priceDistribution", priceDistribution
//        );
//    }


}
