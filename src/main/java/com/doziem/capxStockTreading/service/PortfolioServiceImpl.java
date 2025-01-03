package com.doziem.capxStockTreading.service;

import com.doziem.capxStockTreading.dto.PortfolioDto;
import com.doziem.capxStockTreading.dto.UserDto;
import com.doziem.capxStockTreading.exception.ResourceNotFoundException;
import com.doziem.capxStockTreading.model.Portfolio;
import com.doziem.capxStockTreading.model.User;
import com.doziem.capxStockTreading.repository.PortfolioRepository;
import com.doziem.capxStockTreading.repository.UserRepository;
import com.doziem.capxStockTreading.request.PortfolioRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PortfolioServiceImpl implements IPortfolioService{

    @Autowired
    public PortfolioRepository portfolioRepository;

    @Autowired
    public UserRepository userRepository;

    @Override
    public Portfolio createPortfolio(PortfolioDto portfolioDto, Long userId) {
//
//        if (portfolio.getName() == null || portfolio.getName().isEmpty()) {
//            throw new IllegalArgumentException("Portfolio name cannot be null or empty");
//        }
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//        portfolio.setUser(UserDto.fromEntity(user));
//
//        return portfolioRepository.save(portfolio);

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
}
