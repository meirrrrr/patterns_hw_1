package com.ecommerce.adapters;

import com.ecommerce.interfaces.PaymentMethod;
import com.ecommerce.thirdparty.StripePaymentGateway;
import java.math.BigDecimal;
import java.util.Map;


public class StripePaymentAdapter implements PaymentMethod {
    private final StripePaymentGateway stripeGateway;

    public StripePaymentAdapter(StripePaymentGateway stripeGateway) {
        this.stripeGateway = stripeGateway;
    }

    @Override
    public boolean processPayment(BigDecimal amount, Map<String, Object> details) {
        try {
            String cardNumber = (String) details.get("cardNumber");
            String cvv = (String) details.get("cvv");
            String expiryMonth = (String) details.get("expiryMonth");
            String expiryYear = (String) details.get("expiryYear");

            // Create card token using Stripe's API
            String cardToken = stripeGateway.createCardToken(cardNumber, cvv, expiryMonth, expiryYear);

            // Convert amount to cents (Stripe expects cents)
            double amountInCents = amount.multiply(new BigDecimal("100")).doubleValue();

            // Process payment using Stripe's API
            return stripeGateway.chargeCard(cardToken, amountInCents, "USD");

        } catch (Exception e) {
            System.out.println("Stripe payment failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean validatePaymentDetails(Map<String, Object> details) {
        // Validate required fields for Stripe
        return details.containsKey("cardNumber") &&
               details.containsKey("cvv") &&
               details.containsKey("expiryMonth") &&
               details.containsKey("expiryYear");
    }

    @Override
    public String getPaymentMethodName() {
        return "Stripe Payment Gateway";
    }
}
