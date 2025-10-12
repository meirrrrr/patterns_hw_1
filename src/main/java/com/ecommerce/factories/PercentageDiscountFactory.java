package com.ecommerce.factories;

import com.ecommerce.factories.DiscountStrategyFactory;
import com.ecommerce.models.PercentageDiscount;
import com.ecommerce.interfaces.DiscountStrategy;

import java.math.BigDecimal;


public class PercentageDiscountFactory extends DiscountStrategyFactory {
    private final BigDecimal percentageAmount;

    public PercentageDiscountFactory(BigDecimal percentageAmount) {
        this.percentageAmount = percentageAmount;
    }

    @Override
    public DiscountStrategy createDiscountStrategy() {
        return new PercentageDiscount(percentageAmount);
    }
}
