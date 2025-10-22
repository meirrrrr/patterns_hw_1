package com.ecommerce.bridge;

import com.ecommerce.interfaces.NotificationChannel;
import java.util.Map;


public class PromotionalNotification extends Notification {

    public PromotionalNotification(NotificationChannel channel) {
        super(channel);
    }

    @Override
    public boolean send(String recipient, Map<String, Object> context) {
        if (!isChannelAvailable()) {
            System.out.println("Channel " + getChannelName() + " is not available");
            return false;
        }

        String promoCode = (String) context.getOrDefault("promoCode", "SAVE10");
        String discount = context.getOrDefault("discount", "10%").toString();
        String expiryDate = (String) context.getOrDefault("expiryDate", "limited time");

        String message = String.format("🎉 Special offer! Get %s off your next purchase with code %s. Valid until %s. Shop now!",
                                      discount, promoCode, expiryDate);

        context.put("subject", "Special Offer - " + discount + " Off!");
        context.put("title", "Special Offer");
        context.put("badge", 1);
        context.put("priority", "normal");

        return channel.sendNotification(recipient, message, context);
    }
}
