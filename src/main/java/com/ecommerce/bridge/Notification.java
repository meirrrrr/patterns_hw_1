package com.ecommerce.bridge;

import com.ecommerce.interfaces.NotificationChannel;
import java.util.Map;

public abstract class Notification {
    protected NotificationChannel channel;

    public Notification(NotificationChannel channel) {
        this.channel = channel;
    }

    public abstract boolean send(String recipient, Map<String, Object> context);

    public boolean isChannelAvailable() {
        return channel.isAvailable();
    }

    public String getChannelName() {
        return channel.getChannelName();
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }
}
