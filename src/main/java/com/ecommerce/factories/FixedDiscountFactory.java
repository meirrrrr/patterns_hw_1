package com.ecommerce.factories;

import com.ecommerce.interfaces.DiscountStrategy;
import com.ecommerce.models.FixedAmountDiscount;

import java.math.BigDecimal;

public class FixedDiscountFactory extends DiscountStrategyFactory {
    private final BigDecimal discountAmount;

    public FixedDiscountFactory(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public DiscountStrategy createDiscountStrategy() {
        return new FixedAmountDiscount(discountAmount);
    }
}
