package com.doziem.capxStockTreading.service;

import com.doziem.capxStockTreading.dto.PortfolioDto;
import com.doziem.capxStockTreading.dto.PortfolioMetrics;
import com.doziem.capxStockTreading.dto.StockDto;
import com.doziem.capxStockTreading.exception.ResourceNotFoundException;
import com.doziem.capxStockTreading.model.Portfolio;
import com.doziem.capxStockTreading.model.Stock;
import com.doziem.capxStockTreading.repository.PortfolioRepository;
import com.doziem.capxStockTreading.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class StockServiceImpl implements IStockService{

    @Autowired
    public StockRepository stockRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }



    // Predefined list of available stocks
    private final List<String> availableStocks = List.of("AAPL",  "TSLA", "NFLX", "NVDA", "META");

    @Override
    @Transactional
    public StockDto createStock(StockDto stockDto) {
        if (stockDto == null || stockDto.getPortfolio() == null || stockDto.getPortfolio().getName() == null) {
            throw new IllegalArgumentException("Invalid stock or portfolio details.");
        }

        String portfolioName = stockDto.getPortfolio().getName().trim();

        Portfolio portfolio = portfolioRepository.findByName(portfolioName)
                .orElseGet(() -> portfolioRepository.save(new Portfolio(portfolioName)));

        Stock stock = new Stock();
        stock.setName(stockDto.getName());
        stock.setTicker(stockDto.getTicker());
        stock.setQuantity(stockDto.getQuantity());
        stock.setBuyPrice(stockDto.getBuyPrice());
        stock.setVolume(stockDto.getVolume());
        stock.setPortfolio(portfolio);

        return StockDto.fromStockEntity(stockRepository.save(stock));
    }

    @Override
    public StockDto createNewStock(StockDto stockDto) {
        Stock stock = stockDtoToEntity(stockDto);
        Stock savedStock = stockRepository.save(stock);
        return StockDto.fromStockEntity(savedStock);
    }

    private Stock stockDtoToEntity(StockDto stockDto) {
        Stock stock = new Stock();
        stock.setTicker(stockDto.getTicker());
        stock.setName(stockDto.getName());
        stock.setQuantity(stockDto.getQuantity());
        stock.setBuyPrice(stockDto.getBuyPrice());
        stock.setVolume(stockDto.getVolume());


        if(stockDto.getPortfolio().getName() != null){
            String portfolioName = stockDto.getPortfolio().getName().trim();

            Portfolio portfolio = portfolioRepository.findByName(stock.getPortfolio().getName())
                    .orElseGet(() -> {
                        Portfolio newPortfolio = new Portfolio();
                        newPortfolio.setName(portfolioName);
                        return portfolioRepository.save(newPortfolio);
                    });
            stock.setPortfolio(portfolio);
        }

        return stock;
    }

    @Override
    @Transactional
    public StockDto updateExistingStock(Long id, StockDto stockDto) {
        return stockRepository.findById(id)
                .map(exixtingStock->updateExistingStockFrom(exixtingStock, stockDto))
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
    }

    private StockDto updateExistingStockFrom(Stock existingStock, StockDto request) {

        existingStock.setName(request.getName() != null ? request.getName() : existingStock.getName());
        existingStock.setBuyPrice(request.getBuyPrice() != null ? request.getBuyPrice() : existingStock.getBuyPrice());
        existingStock.setTicker(request.getTicker() != null ? request.getTicker() : existingStock.getTicker());
        existingStock.setQuantity(request.getQuantity() != null ? request.getQuantity() : existingStock.getQuantity());
        existingStock.setVolume(request.getVolume() != null ? request.getVolume() : existingStock.getVolume());

        // Handle portfolio updates
        if (request.getPortfolio() != null) {
            String portfolioName = request.getPortfolio().getName().trim();
            Portfolio portfolio = portfolioRepository.findByName(portfolioName)
                    .orElseGet(() -> {
                        Portfolio newPortfolio = new Portfolio();
                        newPortfolio.setName(portfolioName);
                        return portfolioRepository.save(newPortfolio); // Save only when creating a new one
                    });
            existingStock.setPortfolio(portfolio);
        }

        Stock updatedStock = stockRepository.save(existingStock);
        return StockDto.fromStockEntity(updatedStock);
    }

    @Override
    public List<StockDto> getAllStock() {
        List<Stock> stocks = stockRepository.findAll();
        return stocks.stream()
                .map(StockDto::fromStockEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StockDto getStockById(Long stockId) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(()->new ResourceNotFoundException("Stock not found"));
        return StockDto.fromStockEntity(stock);
    }


    @Override
    public void deleteStock(Long stockId) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
        stockRepository.delete(stock);
    }

    @Override
    public Double calculatePortfolioValue() {
        return stockRepository.findAll().stream()
                .mapToDouble(stock -> stock.getBuyPrice() * stock.getQuantity())
                .sum();
    }

    @Autowired
    public PortfolioMetrics calculatePortfolioMetrics() {
        List<Stock> stocks = stockRepository.findAll();
        Double totalValue = calculatePortfolioValue();
        Integer totalShares = stocks.stream().mapToInt(Stock::getQuantity).sum();
        Double averagePrice = stocks.isEmpty() ? 0.0 : stocks.stream()
                .mapToDouble(Stock::getBuyPrice)
                .average()
                .orElse(0.0);

        Map<String, Double> stockWeights = new HashMap<>();
        for (Stock stock : stocks) {
            double weight = ((stock.getBuyPrice() * stock.getQuantity()) / totalValue) * 100;
            stockWeights.put("name", weight);
        }

        StockDto highestPricedStock = StockDto.fromStockEntity(Objects.requireNonNull(stocks.stream()
                .max(Comparator.comparingDouble(Stock::getBuyPrice))
                .orElse(null)));

        StockDto lowestPricedStock = StockDto.fromStockEntity(Objects.requireNonNull(stocks.stream()
                .min(Comparator.comparingDouble(Stock::getBuyPrice))
                .orElse(null)));

        Map<String, Long> priceDistribution = new LinkedHashMap<>();
        priceDistribution.put("below$50", stocks.stream().filter(s -> s.getBuyPrice() < 50).count());
        priceDistribution.put("between$50And$100", stocks.stream().filter(s -> s.getBuyPrice() >= 50 && s.getBuyPrice() < 100).count());
        priceDistribution.put("above$100", stocks.stream().filter(s -> s.getBuyPrice() >= 100).count());

        return new PortfolioMetrics(totalValue, averagePrice, totalShares, stockWeights,highestPricedStock,
                lowestPricedStock, priceDistribution);
    }


    private final OkHttpClient client = new OkHttpClient();
    private final List<String> availableTickers = Arrays.asList("AAPL", "MSFT", "GOOGL", "AMZN", "META", "TSLA", "NFLX", "NVDA", "ADBE", "INTC");

    public List<Stock> getRandomStocks()  {
        Random random = new Random();
        return availableTickers.stream()
                .distinct()
                .limit(5)
                .map(ticker -> {
                    try {
                        double price = fetchStockPrice(ticker);
                        return new Stock(ticker, ticker, 1, price, null, null);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }


    public double fetchStockPrice(String ticker) throws IOException {
        String url = "https://query1.finance.yahoo.com/v7/finance/quote?symbols=" + ticker;
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                String priceString = responseBody.split("\"regularMarketPrice\":")[1].split(",")[0];
                return Double.parseDouble(priceString);
            }
            throw new IOException("Failed to fetch stock price");
        }
    }

}
