package org.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductRecord {

    private final UUID uuid;
    private final String name;
    private BigDecimal price;
    private final Category category;

    public ProductRecord(UUID uuid, String name, BigDecimal price, Category category) {
        this.uuid = uuid;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public UUID uuid() {
        return uuid;
    }

    public String name() {
        return name;
    }

    public BigDecimal price() {
        return price;
    }

    public Category category() {
        return category;
    }

    public void setPrice(BigDecimal price) {
        this.price = price != null ? price : BigDecimal.ZERO;
    }
}







