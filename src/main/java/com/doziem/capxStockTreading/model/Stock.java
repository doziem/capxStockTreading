package com.doziem.capxStockTreading.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

@Setter
@Getter
@Entity
@AllArgsConstructor
@Table(name = "capx_stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ticker;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double buyPrice;

    @Column(nullable = false)
    private Long volume;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
//    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private Portfolio portfolio;

//    public Stock(String ticker, String ticker1, double price) {
//    }

    public Stock() {

    }

    public Stock(String ticker, String s, int i, double price, Object o, Object o1) {
    }

}
