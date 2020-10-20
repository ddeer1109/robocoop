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
        private final Product product;
        private final BigDecimal quantity;

        public Item(Product product, BigDecimal quantity) {
            this.product = product;
            this.quantity = quantity;
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

        public BigDecimal getMissingToTransactionalQuantity() {
            return new BigDecimal(product.getTransactionalQuantity()).subtract(quantity.remainder(new BigDecimal(product.getTransactionalQuantity())));
        }

        public String getStatus() {
            if (getQuantity().equals(BigDecimal.ZERO)) {
                return "empty";
            }
            if (getMissingToTransactionalQuantity().compareTo(new BigDecimal(product.getTransactionalQuantity())) == 0) {
                return "complete";
            }

            if (getQuantity().compareTo(new BigDecimal(product.getTransactionalQuantity())) < 0) {
                return "incomplete";
            }

            return "complete-incomplete";
        }

    }
}
