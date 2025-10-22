package com.ecommerce.factories;

import com.ecommerce.interfaces.DiscountStrategy;
import com.ecommerce.models.FixedAmountDiscount;
import com.ecommerce.models.PercentageDiscount;

import java.math.BigDecimal;

public abstract class DiscountStrategyFactory {

    public DiscountStrategy getDiscountStrategy() {
        DiscountStrategy strategy = createDiscountStrategy();
        return strategy;
    }

    public abstract DiscountStrategy createDiscountStrategy();
}
