package org.masteukodeu.robocoop.model;

import java.math.BigDecimal;

public class Delivery {

    private final String roundId, productId;
    private final BigDecimal price, amount;

    public Delivery(String roundId, String productId, BigDecimal price, BigDecimal amount) {
        this.roundId = roundId;
        this.productId = productId;
        this.price = price;
        this.amount = amount;
    }

    public String getRoundId() {
        return roundId;
    }

    public String getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
