package com.project.notificationengine.notificationengine.scheduler;

import com.project.notificationengine.notificationengine.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationRetryScheduler {
    @Autowired
    NotificationService notificationService;

    @Scheduled(fixedRate = 900000)
    public void retryFailedNotifications() {
        notificationService.sendNotifications();
    }
}
