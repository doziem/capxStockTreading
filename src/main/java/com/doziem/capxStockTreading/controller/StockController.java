package com.doziem.capxStockTreading.controller;

import com.doziem.capxStockTreading.dto.StockDto;
import com.doziem.capxStockTreading.exception.ResourceNotFoundException;
import com.doziem.capxStockTreading.model.Stock;
import com.doziem.capxStockTreading.response.ApiResponse;
import com.doziem.capxStockTreading.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    public IStockService stockService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createStock(@RequestBody Stock stock){
        try {
            Stock createdStock = stockService.createStock(stock);
            return ResponseEntity.ok(new ApiResponse(createdStock,"Stock successfully Created"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(null, e.getMessage()));
        }
    }

    @GetMapping("/all")
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
            Stock stocks = stockService.getStockById(stockId);
            return ResponseEntity.ok(new ApiResponse(stocks,"Successfully fetched"));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(null,e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(null, e.getMessage()));
        }
    }

    @PutMapping("/{stockId}")
    public ResponseEntity<ApiResponse> updateStock(@PathVariable Long stockId, @RequestBody StockDto updatedStock) {
       try {
           Stock stock = stockService.updateStock(stockId, updatedStock);
           return ResponseEntity.ok(new ApiResponse(stock,"Stock successfully updated"));
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

}
