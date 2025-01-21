package com.doziem.capxStockTreading.dto;

import com.doziem.capxStockTreading.model.Portfolio;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PortfolioDto {
    private Long id;
    private String name;
    private UserDto user;

    public static PortfolioDto fromEntity(Portfolio portfolio) {
        PortfolioDto dto = new PortfolioDto();
        dto.setId(portfolio.getId());
        dto.setName(portfolio.getName());
        if (portfolio.getUser() != null) {
            dto.setUser(UserDto.fromEntity(portfolio.getUser()));
        }else {
            dto.setUser(null);
        }
        return dto;
    }

    public static Portfolio toEntity(PortfolioDto dto) {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(dto.getId());
        portfolio.setName(dto.getName());
        return portfolio;
    }

}
