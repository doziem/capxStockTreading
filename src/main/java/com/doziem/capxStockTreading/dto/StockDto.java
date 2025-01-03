package com.doziem.capxStockTreading.dto;

import com.doziem.capxStockTreading.model.Stock;

public class StockDto {

    private Long id;
    private String ticker;
    private String name;
    private Integer quantity;
    private Double buyPrice;
    private Long volume;
    private PortfolioDto portfolio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public PortfolioDto getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(PortfolioDto portfolio) {
        this.portfolio = portfolio;
    }

    public static StockDto fromEntity(Stock stock) {
        StockDto dto = new StockDto();
        dto.setId(stock.getId());
        dto.setTicker(stock.getTicker());
        dto.setName(stock.getName());
        dto.setQuantity(stock.getQuantity());
        dto.setBuyPrice(stock.getBuyPrice());
        dto.setVolume(stock.getVolume());
        if (stock.getPortfolio() != null) {
            dto.setPortfolio(PortfolioDto.fromEntity(stock.getPortfolio()));
        }
        return dto;
    }
}
