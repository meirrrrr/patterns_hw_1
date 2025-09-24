package com.ecommerce.models;

import com.ecommerce.interfaces.Payable;
import com.ecommerce.interfaces.Discountable;
import com.ecommerce.interfaces.DiscountStrategy;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Payable, Discountable {
    private List<Product> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Product product) {
        items.add(product);
    }

    public void removeItem(Product product) {
        items.remove(product);
    }

    public List<Product> getItems() {
        return new ArrayList<>(items);
    }

    @Override
    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal applyDiscount(DiscountStrategy discountStrategy) {
        BigDecimal originalTotal = getTotalAmount();
        BigDecimal discount = discountStrategy.calculateDiscount(originalTotal);
        return originalTotal.subtract(discount);
    }

    public int getItemCount() {
        return items.size();
    }
}
