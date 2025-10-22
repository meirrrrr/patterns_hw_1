package com.ecommerce.thirdparty;

import java.math.BigDecimal;


public class StripePaymentGateway {

    public boolean chargeCard(String cardToken, double amountInCents, String currency) {
        System.out.println("Stripe: Charging card token " + cardToken +
                          " for " + amountInCents + " cents in " + currency);
        // simulate successful payment
        return true;
    }

    public boolean validateCardToken(String cardToken) {
        return cardToken != null && cardToken.startsWith("tok_");
    }

    public String createCardToken(String cardNumber, String cvv, String expiryMonth, String expiryYear) {
        // simulate token creation
        return "tok_" + cardNumber.substring(cardNumber.length() - 4);
    }
}
