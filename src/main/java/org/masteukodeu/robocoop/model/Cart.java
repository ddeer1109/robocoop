package org.masteukodeu.robocoop.model;

import java.math.BigDecimal;
import java.util.List;

public class Cart {

    public static final BigDecimal COMMUNITY_FUND_RATE = new BigDecimal("0.1");

    private final List<Item> items;

    public Cart(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public BigDecimal getTotal() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Item item : items) {
            sum = sum.add(item.getAmount());
        }
        return sum;
    }

    public BigDecimal getCommunityFund() {
        return getTotal().multiply(COMMUNITY_FUND_RATE);
    }

    public static class Item {
        private final String orderId;
        private final Product product;
        private final BigDecimal quantity;

        public Item(String orderId, Product product, BigDecimal quantity) {
            this.orderId = orderId;
            this.product = product;
            this.quantity = quantity;
        }

        public String getOrderId() {
            return orderId;
        }

        public Product getProduct() {
            return product;
        }

        public BigDecimal getQuantity() {
            return quantity;
        }

        public BigDecimal getAmount() {
            return product.getPrice().multiply(quantity);
        }

    }
}
