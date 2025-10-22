package com.ecommerce.bridge;

import com.ecommerce.interfaces.NotificationChannel;
import java.util.Map;

public class OrderConfirmationNotification extends Notification {

    public OrderConfirmationNotification(NotificationChannel channel) {
        super(channel);
    }

    @Override
    public boolean send(String recipient, Map<String, Object> context) {
        if (!isChannelAvailable()) {
            System.out.println("Channel " + getChannelName() + " is not available");
            return false;
        }

        String orderId = (String) context.getOrDefault("orderId", "N/A");
        String totalAmount = context.getOrDefault("totalAmount", "0.00").toString();

        String message = String.format("Your order #%s has been confirmed! Total: $%s. Thank you for your purchase!",
                                      orderId, totalAmount);

        context.put("subject", "Order Confirmation - Order #" + orderId);
        context.put("title", "Order Confirmed");
        context.put("badge", 1);

        return channel.sendNotification(recipient, message, context);
    }
}
