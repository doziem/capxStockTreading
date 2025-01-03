package com.doziem.capxStockTreading.dto;

import com.doziem.capxStockTreading.model.Portfolio;

public class PortfolioDto {
    private Long id;
    private String name;
    private UserDto user;

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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public static PortfolioDto fromEntity(Portfolio portfolio) {
        PortfolioDto dto = new PortfolioDto();
        dto.setId(portfolio.getId());
        dto.setName(portfolio.getName());
        if (portfolio.getUser() != null) {
            dto.setUser(UserDto.fromEntity(portfolio.getUser()));
        }
        return dto;
    }

}
