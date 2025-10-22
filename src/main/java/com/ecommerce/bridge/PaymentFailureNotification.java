package com.ecommerce.bridge;

import com.ecommerce.interfaces.NotificationChannel;
import java.util.Map;


public class PaymentFailureNotification extends Notification {

    public PaymentFailureNotification(NotificationChannel channel) {
        super(channel);
    }

    @Override
    public boolean send(String recipient, Map<String, Object> context) {
        if (!isChannelAvailable()) {
            System.out.println("Channel " + getChannelName() + " is not available");
            return false;
        }

        String orderId = (String) context.getOrDefault("orderId", "N/A");
        String reason = (String) context.getOrDefault("failureReason", "Unknown error");

        String message = String.format("Payment failed for order #%s. Reason: %s. Please try again or use a different payment method.",
                                      orderId, reason);

        context.put("subject", "Payment Failed - Order #" + orderId);
        context.put("title", "Payment Failed");
        context.put("badge", 1);
        context.put("priority", "high");

        return channel.sendNotification(recipient, message, context);
    }
}
