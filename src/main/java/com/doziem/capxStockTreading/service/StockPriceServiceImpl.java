package com.doziem.capxStockTreading.service;

import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

@Service
public class StockPriceServiceImpl implements IStockPriceService{
    @Override
    public Double fetchStockPrice(String ticker) {
        try {
            Stock stock = YahooFinance.get(ticker);
            return stock.getQuote().getPrice().doubleValue();
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch stock price for ticker: " + ticker, e);
        }
    }

}
