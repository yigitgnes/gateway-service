package com.atech.calculator.rest.client.item.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Sale {

    public Long id;
    public BigDecimal purchasePrice;
    public BigDecimal salePrice;
    public Instant purchaseDate;
    public Instant saleDate;
}
