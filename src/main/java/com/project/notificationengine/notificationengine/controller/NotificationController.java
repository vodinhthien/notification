package com.project.notificationengine.notificationengine.controller;

import com.project.notificationengine.notificationengine.model.Notification;
import com.project.notificationengine.notificationengine.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    @Autowired
    private final NotificationService notificationService;

    /**
     * API to retrieve all sent notifications
     * @return List of notifications
     */
    @GetMapping("/all")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    /**
     * API to trigger the sending of pending notifications
     */
    @PostMapping("/trigger-resend-pending")
    public void sendNotifications() {
        notificationService.sendNotifications();
    }
    /**
     * API to publish a message to a topic
     * @param topic The topic name
     * @param subject The subject of the message
     * @param message The content of the message
     */
    @PostMapping("/publish")
    public ResponseEntity<Void> publishMessage(@RequestParam String topic, @RequestParam String subject, @RequestParam String message) {
        notificationService.publish(topic, subject, message);
        return ResponseEntity.ok().build();
    }
}
