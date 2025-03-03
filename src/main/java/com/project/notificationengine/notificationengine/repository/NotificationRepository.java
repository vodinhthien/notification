package com.project.notificationengine.notificationengine.repository;

import com.project.notificationengine.notificationengine.model.Notification;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@Repository
public class NotificationRepository {
    private final List<Notification> notifications = new CopyOnWriteArrayList<>();

    public void save(Notification notification) {
        notifications.add(notification);
    }

    public List<Notification> findAll() {
        return notifications;
    }

    public List<Notification> findPendingNotifications() {
        return notifications.stream()
                .filter(noti -> !noti.isDelivered() && noti.getRetryCount() < 10)
                .toList();
    }

    public void update(Notification notification) {
        notifications.replaceAll(n -> n.getId().equals(notification.getId()) ? notification : n);
    }
}
