package com.ecommerce.implementations;

import com.ecommerce.interfaces.NotificationChannel;
import java.util.Map;


public class EmailNotification implements NotificationChannel {

    @Override
    public boolean sendNotification(String recipient, String message, Map<String, Object> context) {
        System.out.println("📧 Email sent to: " + recipient);
        System.out.println("Subject: " + context.getOrDefault("subject", "E-commerce Notification"));
        System.out.println("Message: " + message);
        return true;
    }

    @Override
    public boolean isAvailable() {
        return true; // Assume email service is always available
    }

    @Override
    public String getChannelName() {
        return "Email";
    }
}
