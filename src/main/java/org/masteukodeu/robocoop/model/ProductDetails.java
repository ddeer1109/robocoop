package org.masteukodeu.robocoop.model;

import java.math.BigDecimal;

public class    ProductDetails {
    private final Product product;
    private final BigDecimal totalQuantity;
    private final Delivery delivery;

    public ProductDetails(Product product, BigDecimal orderedQuantity, Delivery delivery) {
        this.product = product;
        this.totalQuantity = orderedQuantity;
        this.delivery = delivery;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public BigDecimal getMissingToTransactionalQuantity() {
        return new BigDecimal(product.getTransactionalQuantity()).subtract(totalQuantity.remainder(new BigDecimal(product.getTransactionalQuantity())));
    }

    public String getStatus() {
        if (totalQuantity.equals(BigDecimal.ZERO)) {
            return "empty";
        }
        if (getMissingToTransactionalQuantity().compareTo(new BigDecimal(product.getTransactionalQuantity())) == 0) {
            return "complete";
        }

        if (totalQuantity.compareTo(new BigDecimal(product.getTransactionalQuantity())) < 0) {
            return "incomplete";
        }

        return "complete-incomplete";
    }
}
