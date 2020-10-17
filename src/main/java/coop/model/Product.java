package coop.model;

import java.math.BigDecimal;

public class Product {

    private String id;
    private String name;
    private BigDecimal price;
    private String unit;

    public Product(String id, String name, BigDecimal price, String unit) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
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
}
