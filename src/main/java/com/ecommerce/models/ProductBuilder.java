package com.ecommerce.models;

import java.math.BigDecimal;

public class ProductBuilder {
    private String id;
    private String name;
    private BigDecimal price;

    public ProductBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Product build() {
        return new Product(id, name, price);
    }
}
