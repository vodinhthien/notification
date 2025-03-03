package com.project.notificationengine.notificationengine.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Notification {
    private String id = UUID.randomUUID().toString();
    private String topic;
    private String subject;
    private String message;
    private String subscriberUrl;
    private boolean sent = false;  // Has the notification been distributed?
    private boolean delivered = false;  // Has the client received it?
    private int retryCount = 0;
    private LocalDateTime createdAt = LocalDateTime.now(); // Time of creation
    private LocalDateTime lastAttemptTime; // Last attempt to send
    private LocalDateTime deliveredAt; // Time of successful delivery
    private List<String> history = new ArrayList<>(); // Retry history
}
