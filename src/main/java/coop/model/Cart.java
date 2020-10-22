package coop.model;

import java.math.BigDecimal;
import java.util.List;

public class Cart {

    private List<Item> items;

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
