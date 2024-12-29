package com.doziem.capxStockTreading.service;

import com.doziem.capxStockTreading.exception.ResourceNotFoundException;
import com.doziem.capxStockTreading.model.Portfolio;
import com.doziem.capxStockTreading.model.Stock;
import com.doziem.capxStockTreading.model.User;
import com.doziem.capxStockTreading.repository.PortfolioRepository;
import com.doziem.capxStockTreading.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PortfolioServiceImpl implements IPortfolioService{

    @Autowired
    public PortfolioRepository portfolioRepository;

    @Autowired
    public UserRepository userRepository;
    @Override
    public Portfolio createPortfolio(Long userId, Portfolio portfolio) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        portfolio.setUser(user);

        // Save the portfolio
        return portfolioRepository.save(portfolio);
    }

    @Override
    public List<Portfolio> getPortfoliosByUser(Long userId) {
        return portfolioRepository.findByUserId(userId);
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
}
