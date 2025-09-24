package com.ecommerce.interfaces;

import java.math.BigDecimal;

public interface Discountable {
    BigDecimal applyDiscount(DiscountStrategy discountStrategy);
}
