package com.project.notificationengine.notificationengine.service;

import com.project.notificationengine.notificationengine.model.Notification;
import com.project.notificationengine.notificationengine.model.Subscription;
import com.project.notificationengine.notificationengine.repository.NotificationRepository;
import com.project.notificationengine.notificationengine.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Sends notifications to all subscribers of a given topic.
     */
    /**
     * Creates notifications for all subscribers of a topic and attempts to send them.
     * @param topic The topic name
     * @param subject The notification subject
     * @param message The notification content
     */
    public void publish(String topic, String subject, String message) {
        log.info("Creating notifications for topic: {}", topic);

        // Get all subscribers of the topic
        List<Subscription> subscribers = subscriptionRepository.findByTopic(topic);

        if (subscribers.isEmpty()) {
            log.warn("No subscribers found for topic: {}", topic);
            return;
        }

        // Create notifications for each subscriber
        for (Subscription subscriber : subscribers) {
            Notification notification = new Notification();
            notification.setTopic(topic);
            notification.setSubject(subject);
            notification.setMessage(message);
            notification.setSubscriberUrl(subscriber.getUrl());
            notification.setCreatedAt(LocalDateTime.now());
            notification.setLastAttemptTime(null);
            notificationRepository.save(notification);
        }

        log.info("Created {} notifications for topic {}", subscribers.size(), topic);

        // Now attempt to send the pending notifications
        sendPendingNotifications(topic);
    }


    /**
     * Sends all pending notifications for a specific topic.
     * @param topic The topic for which to send pending notifications.
     */
    @Async
    public void sendPendingNotifications(String topic) {
        try{
            List<Notification> notifications = notificationRepository.findPendingNotifications();

            if (notifications.isEmpty()) {
                log.info("No pending notifications to send.");
                return;
            }

            for (Notification notification : notifications) {
                if(topic!=null){
                    if (!notification.getTopic().equals(topic)) continue;
                }
                notification.setLastAttemptTime(LocalDateTime.now());
                notification.getHistory().add("Attempt " + notification.getRetryCount() + " at " + LocalDateTime.now());

                try {
                    notification.setSent(true);
                    restTemplate.postForEntity(notification.getSubscriberUrl(), notification.getMessage(), Void.class);

                    notification.setDelivered(true);
                    notification.setDeliveredAt(LocalDateTime.now());
                    log.info("Successfully sent notification to: {}", notification.getSubscriberUrl());
                } catch (Exception e) {
                    notification.setRetryCount(notification.getRetryCount() + 1);
                    notification.getHistory().add("Error: " + e.getMessage());
                    log.warn("Failed to send notification to {}. Retry count: {}", notification.getSubscriberUrl(), notification.getRetryCount());
                }

                notificationRepository.update(notification);
            }
        }catch (Exception ex){
            log.error("Exception", ex);
        }

    }

    /**
     * Retrieves all notifications.
     * @return List of notifications.
     */
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    /**
     * Sends pending notifications asynchronously.
     */
    @Async
    public void sendNotifications() {
        this.sendPendingNotifications(null);
//        List<Notification> notifications = notificationRepository.findPendingNotifications();
//        for (Notification notification : notifications) {
//            try {
//                notification.setSent(true);
//                restTemplate.postForEntity(notification.getSubscriberUrl(), notification, Void.class);
//                notification.setDelivered(true);
//            } catch (Exception e) {
//                notification.setRetryCount(notification.getRetryCount() + 1);
//                notification.setDelivered(false);
//                notification.setLastAttemptTime(LocalDateTime.now());
//                notification.getHistory().add("Error: " + e.getMessage());
//            }
//            notificationRepository.update(notification);
//        }
    }
}
