package coop.model;

import java.math.BigDecimal;

public class Product {

    private String id;
    private String name;
    private BigDecimal price;
    private String unit;
    private String categoryId;
    private int transactionalQuantity;

    public Product(String id, String name, BigDecimal price, String unit, String categoryId, int transactionalQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.categoryId = categoryId;
        this.transactionalQuantity = transactionalQuantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getUnit() {
        return unit;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public int getTransactionalQuantity() {
        return transactionalQuantity;
    }
}
