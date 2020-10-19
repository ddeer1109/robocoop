package coop.model;

import java.math.BigDecimal;

public class Order {
    private final String productId, roundId, userId;

    private final BigDecimal quantity;

    public Order(String productId, String roundId, String userId, BigDecimal quantity) {
        this.productId = productId;
        this.roundId = roundId;
        this.userId = userId;
        this.quantity = quantity;
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
