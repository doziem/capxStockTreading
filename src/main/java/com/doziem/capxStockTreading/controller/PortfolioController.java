package com.doziem.capxStockTreading.controller;

import com.doziem.capxStockTreading.dto.PortfolioDto;
import com.doziem.capxStockTreading.dto.PortfolioMetrics;
import com.doziem.capxStockTreading.exception.ResourceNotFoundException;
import com.doziem.capxStockTreading.model.Portfolio;
import com.doziem.capxStockTreading.response.ApiResponse;
import com.doziem.capxStockTreading.service.IPortfolioService;
import com.doziem.capxStockTreading.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;



import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    private IPortfolioService portfolioService;


    private final IStockService stockService;

    public PortfolioController(IStockService stockService){
        this.stockService = stockService;
    }

    /**
     * Create a new portfolio for a user.
     */
    @PostMapping("/users/{userId}/portfolios")
    public ResponseEntity<ApiResponse> createPortfolio(
            @RequestBody PortfolioDto portfolio,
            @PathVariable Long userId) {
        try{
            Portfolio newPortfolio = portfolioService.createPortfolio(portfolio, userId);
            return ResponseEntity.ok(new ApiResponse(newPortfolio,"Portfolio created successfully"));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(null, e.getMessage()));
        }
    }

    /**
     * Get all portfolios for a user.
     */

    @GetMapping("/all")
    public ResponseEntity<List<PortfolioDto>> getAllPortfolios() {
        List<PortfolioDto> portfolios = portfolioService.getAllPortfolio();
        return ResponseEntity.ok(portfolios);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Portfolio>> getPortfoliosByUser(@PathVariable Long userId) {
        List<Portfolio> portfolios = portfolioService.getPortfoliosByUser(userId);
        return ResponseEntity.ok(portfolios);
    }

    /**
     * Update a portfolio.
     */
    @PutMapping("/{portfolioId}")
    public ResponseEntity<Portfolio> updatePortfolio(@PathVariable Long portfolioId, @RequestBody Portfolio updatedPortfolio) {
        Portfolio portfolio = portfolioService.updatePortfolio(portfolioId, updatedPortfolio);
        return ResponseEntity.ok(portfolio);
    }

    /**
     * Delete a portfolio.
     */
    @DeleteMapping("/{portfolioId}")
    public ResponseEntity<Void> deletePortfolio(@PathVariable Long portfolioId) {
        portfolioService.deletePortfolio(portfolioId);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/metrics")
//    public ResponseEntity<ApiResponse> calculatePortfolioMetrics() {
//
//        try {
//            portfolioService.calculatePortfolioMetrics();
//            return ResponseEntity.ok(new ApiResponse( portfolioService
//                    .calculatePortfolioMetrics(),"Portfolio metric fetched"));
//        }catch (ResourceNotFoundException e){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ApiResponse(null, e.getMessage()));
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponse(null, e.getMessage()));
//        }
//    }

    @GetMapping("/portfolio-value")
    public Double getPortfolioValue() {
        return stockService.calculatePortfolioValue();
    }

    @GetMapping("/portfolio-metrics")
    public PortfolioMetrics getPortfolioMetrics() {
        return stockService.calculatePortfolioMetrics();
    }
}
