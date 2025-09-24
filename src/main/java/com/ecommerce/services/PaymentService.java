package com.ecommerce.services;

import com.ecommerce.interfaces.PaymentMethod;
import com.ecommerce.interfaces.Payable;
import java.math.BigDecimal;
import java.util.Map;

public class PaymentService {
    private PaymentMethod paymentMethod;

    public PaymentService(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean processPayment(Payable payableItem, Map<String, Object> paymentDetails) {
        BigDecimal amount = payableItem.getTotalAmount();

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Invalid payment amount: " + amount);
            return false;
        }

        if (!paymentMethod.validatePaymentDetails(paymentDetails)) {
            System.out.println("Invalid payment details");
            return false;
        }

        boolean success = paymentMethod.processPayment(amount, paymentDetails);

        if (success) {
            System.out.println("Payment of $" + amount + " processed successfully using " +
                             paymentMethod.getPaymentMethodName());
        } else {
            System.out.println("Payment failed");
        }

        return success;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
