package org.masteukodeu.robocoop.model;

import java.math.BigDecimal;

public class Order {
    private final String id, productId, roundId, userId;

    private final BigDecimal quantity;

    public Order(String id, String productId, String roundId, String userId, BigDecimal quantity) {
        this.id = id;
        this.productId = productId;
        this.roundId = roundId;
        this.userId = userId;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getRoundId() {
        return roundId;
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
}
