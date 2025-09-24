package com.ecommerce.interfaces;

import java.util.Map;

public interface NotificationChannel {
    boolean sendNotification(String recipient, String message, Map<String, Object> context);
    boolean isAvailable();
    String getChannelName();
}
