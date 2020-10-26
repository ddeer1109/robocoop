package coop.model;

import java.math.BigDecimal;

public class ProductDetails {
    private final Product product;
    private final BigDecimal totalQuantity;

    public ProductDetails(Product product, BigDecimal orderedQuantity) {
        this.product = product;
        this.totalQuantity = orderedQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
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
