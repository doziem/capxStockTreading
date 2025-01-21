package com.doziem.capxStockTreading.service;

import com.doziem.capxStockTreading.exception.ResourceNotFoundException;
import com.doziem.capxStockTreading.model.Portfolio;
import com.doziem.capxStockTreading.model.Stock;
import com.doziem.capxStockTreading.model.User;
import com.doziem.capxStockTreading.repository.PortfolioRepository;
import com.doziem.capxStockTreading.repository.StockRepository;
import com.doziem.capxStockTreading.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PortfolioValueServiceImpl implements IPortfolioValueService{
    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private IStockPriceService stockPriceService;


    private final List<String> availableStocks = List.of("AAPL", "GOOGL", "MSFT", "AMZN", "TSLA", "FB", "NFLX", "NVDA", "AMD", "INTC");
    @Override
    public Portfolio assignStocksToUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Portfolio portfolio = new Portfolio();
        portfolio.setName("User Portfolio");
        portfolio.setUser(user);

        List<Stock> stocks = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            String ticker = availableStocks.get(random.nextInt(availableStocks.size()));

            Stock stock = new Stock();
            stock.setTicker(ticker);
            // Replace with real stock names if needed
            stock.setName("Stock " + ticker);
            // Assignment: Each stock has a quantity of 1
            stock.setQuantity(1);
            stock.setBuyPrice(stockPriceService.fetchStockPrice(ticker));
            stock.setPortfolio(portfolio);

            stocks.add(stock);
        }

        portfolio.setStocks(stocks);
        return portfolioRepository.save(portfolio);
    }

    @Override
    public Double calculatePortfolioValue(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));

        return portfolio.getStocks().stream()
                .mapToDouble(stock -> stockPriceService.fetchStockPrice(stock.getTicker()) * stock.getQuantity())
                .sum();

    }

}
