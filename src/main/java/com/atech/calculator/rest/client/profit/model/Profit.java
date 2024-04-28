package com.atech.calculator.rest.client.profit.model;

import jakarta.enterprise.context.RequestScoped;

import java.math.BigDecimal;

@RequestScoped
public class Profit {
    public BigDecimal totalEarning;
    public BigDecimal totalStaticSpending;
    public BigDecimal totalNonStaticSpending;
    public BigDecimal profit;

    public Profit() {
    }
}