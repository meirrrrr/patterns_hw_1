package com.ecommerce.interfaces;

import java.math.BigDecimal;
import java.util.Map;


public interface DiscountStrategy {
    BigDecimal calculateDiscount(BigDecimal amount);
    boolean isApplicable(Map<String, Object> context);
    String getDiscountDescription();
}
