package com.doziem.capxStockTreading.dto;

import com.doziem.capxStockTreading.model.Stock;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockDto {

    private Long id;
    private String ticker;
    private String name;
    private Integer quantity;
    private Double buyPrice;
    private Long volume;
    private PortfolioDto portfolio;

    public static StockDto fromStockEntity(Stock stock) {
        StockDto dto = new StockDto();
        dto.setId(stock.getId());
        dto.setTicker(stock.getTicker());
        dto.setName(stock.getName());
        dto.setQuantity(stock.getQuantity());
        dto.setBuyPrice(stock.getBuyPrice());
        dto.setVolume(stock.getVolume());
        if (stock.getPortfolio().getName() != null) {
            dto.setPortfolio(PortfolioDto.fromEntity(stock.getPortfolio()));
        }else {
            dto.setPortfolio(null);
        }
        return dto;
    }
}
