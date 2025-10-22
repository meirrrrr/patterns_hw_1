package com.ecommerce.adapters;

import com.ecommerce.interfaces.PaymentMethod;
import com.ecommerce.thirdparty.SquarePaymentProcessor;
import java.math.BigDecimal;
import java.util.Map;


public class SquarePaymentAdapter implements PaymentMethod {
    private final SquarePaymentProcessor squareProcessor;
    private final String merchantId;

    public SquarePaymentAdapter(SquarePaymentProcessor squareProcessor, String merchantId) {
        this.squareProcessor = squareProcessor;
        this.merchantId = merchantId;
    }

    @Override
    public boolean processPayment(BigDecimal amount, Map<String, Object> details) {
        try {
            String cardNumber = (String) details.get("cardNumber");
            String cvv = (String) details.get("cvv");

            // Format card details using Square's format
            String formattedCardDetails = squareProcessor.formatCardDetails(cardNumber, cvv);

            // Process transaction using Square's API
            String transactionId = squareProcessor.processTransaction(amount, formattedCardDetails, merchantId);

            return squareProcessor.isTransactionSuccessful(transactionId);

        } catch (Exception e) {
            System.out.println("Square payment failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean validatePaymentDetails(Map<String, Object> details) {
        // Validate required fields for Square
        if (!details.containsKey("cardNumber") || !details.containsKey("cvv")) {
            return false;
        }

        String cardNumber = (String) details.get("cardNumber");
        String cvv = (String) details.get("cvv");
        String formattedCardDetails = squareProcessor.formatCardDetails(cardNumber, cvv);

        return squareProcessor.verifyCardDetails(formattedCardDetails);
    }

    @Override
    public String getPaymentMethodName() {
        return "Square Payment Processor";
    }
}
