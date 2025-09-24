package com.ecommerce.implementations;

import com.ecommerce.interfaces.PaymentMethod;
import java.math.BigDecimal;
import java.util.Map;

public class CreditCardPayment implements PaymentMethod {

    @Override
    public boolean processPayment(BigDecimal amount, Map<String, Object> details) {
        System.out.println("Processing credit card payment of $" + amount);
        return true;
    }

    @Override
    public boolean validatePaymentDetails(Map<String, Object> details) {
        return details.containsKey("cardNumber") && details.containsKey("cvv");
    }

    @Override
    public String getPaymentMethodName() {
        return "Credit Card";
    }
}
