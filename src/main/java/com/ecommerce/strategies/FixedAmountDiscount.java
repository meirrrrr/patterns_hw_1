package com.ecommerce.strategies;

import com.ecommerce.interfaces.DiscountStrategy;
import java.math.BigDecimal;
import java.util.Map;

public class FixedAmountDiscount implements DiscountStrategy {
    private final BigDecimal discountAmount;

    public FixedAmountDiscount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal amount) {
        return amount.compareTo(discountAmount) >= 0 ? discountAmount : amount;
    }

    @Override
    public boolean isApplicable(Map<String, Object> context) {
        return true;
    }

    @Override
    public String getDiscountDescription() {
        return "$" + discountAmount + " off";
    }
}
