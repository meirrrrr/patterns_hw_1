package com.ecommerce.implementations;

import com.ecommerce.interfaces.NotificationChannel;
import java.util.Map;


public class SMSNotification implements NotificationChannel {

    @Override
    public boolean sendNotification(String recipient, String message, Map<String, Object> context) {
        System.out.println("📱 SMS sent to: " + recipient);
        System.out.println("Message: " + message);
        System.out.println("Provider: " + context.getOrDefault("provider", "Default SMS Provider"));
        return true;
    }

    @Override
    public boolean isAvailable() {
        return Math.random() > 0.1;
    }

    @Override
    public String getChannelName() {
        return "SMS";
    }
}
