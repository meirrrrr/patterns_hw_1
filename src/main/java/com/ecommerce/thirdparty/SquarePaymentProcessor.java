package com.ecommerce.thirdparty;

import java.math.BigDecimal;

public class SquarePaymentProcessor {

    public String processTransaction(BigDecimal amount, String cardDetails, String merchantId) {
        System.out.println("Square: Processing transaction for merchant " + merchantId +
                          " - Amount: $" + amount + " with card: " + cardDetails);
        // simulate successful transaction
        return "txn_" + System.currentTimeMillis();
    }

    public boolean verifyCardDetails(String cardDetails) {
        return cardDetails != null && cardDetails.contains("|");
    }

    public String formatCardDetails(String cardNumber, String cvv) {
        return cardNumber + "|" + cvv;
    }

    public boolean isTransactionSuccessful(String transactionId) {
        return transactionId != null && transactionId.startsWith("txn_");
    }
}
