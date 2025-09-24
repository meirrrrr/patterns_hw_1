package com.ecommerce.interfaces;

import java.math.BigDecimal;
import java.util.Map;

public interface PaymentMethod {
    boolean processPayment(BigDecimal amount, Map<String, Object> details);
    boolean validatePaymentDetails(Map<String, Object> details);
    String getPaymentMethodName();
}
