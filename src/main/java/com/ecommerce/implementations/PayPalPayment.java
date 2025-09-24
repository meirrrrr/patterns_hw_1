package com.ecommerce.implementations;

import com.ecommerce.interfaces.PaymentMethod;
import java.math.BigDecimal;
import java.util.Map;

public class PayPalPayment implements PaymentMethod {

    @Override
    public boolean processPayment(BigDecimal amount, Map<String, Object> details) {
        System.out.println("Processing PayPal payment of $" + amount);
        return true;
    }

    @Override
    public boolean validatePaymentDetails(Map<String, Object> details) {
        return details.containsKey("email");
    }

    @Override
    public String getPaymentMethodName() {
        return "PayPal";
    }
}
