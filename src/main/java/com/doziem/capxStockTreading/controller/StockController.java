package com.doziem.capxStockTreading.controller;

import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.doziem.capxStockTreading.dto.StockDto;
import com.doziem.capxStockTreading.exception.ResourceNotFoundException;
import com.doziem.capxStockTreading.model.Stock;
import com.doziem.capxStockTreading.response.ApiResponse;
import com.doziem.capxStockTreading.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin(origins = "http://localhost:3000")
public class StockController {

    @Autowired
    public IStockService stockService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createStock(@RequestBody StockDto stock){
        try {
            StockDto createdStock = stockService.createStock(stock);
            return ResponseEntity.ok(new ApiResponse(createdStock,"Stock successfully Created"));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(null, e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(null, e.getMessage()));
        }
    }

    @GetMapping("/all/stock")
    public ResponseEntity<ApiResponse> getAllStocks() {
        try {
            List<StockDto> stocks = stockService.getAllStock();
            return ResponseEntity.ok(new ApiResponse(stocks,"All Stock fetched"));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(null, e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(null, e.getMessage()));
        }
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<ApiResponse> getStock(@PathVariable Long stockId) {
        try{
            StockDto stocks = stockService.getStockById(stockId);
            return ResponseEntity.ok(new ApiResponse(stocks,"Successfully fetched"));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(null,e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(null, e.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createNewStock(@RequestBody StockDto stockDto) {
        try {
            StockDto createdStock = stockService.createNewStock(stockDto);
            return ResponseEntity.ok(new ApiResponse(createdStock,"Stock successfully Created"));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(null, e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(null, e.getMessage()));
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateExistingStock(@PathVariable Long id, @RequestBody StockDto stockDto) {
       try{
           StockDto updatedStock = stockService.updateExistingStock(id, stockDto);
           return ResponseEntity.ok(new ApiResponse(updatedStock,"Stock successfully updated"));
       }catch (ResourceNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
    }catch (Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(null, e.getMessage()));
    }

    }

    @DeleteMapping("/{stockId}")
    public ResponseEntity<ApiResponse> deleteStock(@PathVariable Long stockId) {
        try {
            stockService.deleteStock(stockId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(null,"Stock deleted successfully"));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(null, e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(null, e.getMessage()));
        }
    }

    @GetMapping("/portfolio")
    public List<Stock> getUserPortfolio() throws IOException {
        return stockService.getRandomStocks();
    }

    @GetMapping("/portfolio/value")
    public double getPortfolioValue() throws IOException {
        return stockService.getRandomStocks().stream()
                .mapToDouble(Stock::getBuyPrice)
                .sum();
    }


}
