package com.doziem.capxStockTreading.dto;

import com.doziem.capxStockTreading.model.Stock;
import lombok.Getter;

import java.util.Map;
@Getter
public class PortfolioMetrics {
    private final Double totalValue;
    private final Double averagePrice;
    private final Integer totalShares;
    private final Map<String, Double> stockWeights;
    private final StockDto highestPricedStock;
    private final StockDto lowestPricedStock;
    private final Map<String, Long> priceDistribution;

    public PortfolioMetrics(Double totalValue, Double averagePrice, Integer totalShares, Map<String, Double> stockWeights, StockDto highestPricedStock,
                            StockDto lowestPricedStock, Map<String, Long> priceDistribution) {
        this.totalValue = totalValue;
        this.averagePrice = averagePrice;
        this.totalShares = totalShares;
        this.stockWeights = stockWeights;
        this.highestPricedStock = highestPricedStock;
        this.lowestPricedStock = lowestPricedStock;
        this.priceDistribution = priceDistribution;
    }

}