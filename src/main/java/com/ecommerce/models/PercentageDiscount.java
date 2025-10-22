package com.ecommerce.models;

import com.ecommerce.interfaces.DiscountStrategy;
import java.math.BigDecimal;
import java.util.Map;

public class PercentageDiscount implements DiscountStrategy {
    private final BigDecimal percentage;

    public PercentageDiscount(BigDecimal percentage) {
        this.percentage = percentage;
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal amount) {
        return amount.multiply(percentage).divide(BigDecimal.valueOf(100));
    }

    @Override
    public boolean isApplicable(Map<String, Object> context) {
        return true;
    }

    @Override
    public String getDiscountDescription() {
        return "Discount: " + percentage + "% (Percentage)";
    }
}
