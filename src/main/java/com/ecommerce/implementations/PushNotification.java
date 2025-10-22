package com.ecommerce.implementations;

import com.ecommerce.interfaces.NotificationChannel;
import java.util.Map;


public class PushNotification implements NotificationChannel {

    @Override
    public boolean sendNotification(String recipient, String message, Map<String, Object> context) {
        System.out.println("🔔 Push notification sent to device: " + recipient);
        System.out.println("Title: " + context.getOrDefault("title", "E-commerce Alert"));
        System.out.println("Message: " + message);
        System.out.println("Badge count: " + context.getOrDefault("badge", 1));
        return true;
    }

    @Override
    public boolean isAvailable() {
        return Math.random() > 0.05;
    }

    @Override
    public String getChannelName() {
        return "Push Notification";
    }
}
