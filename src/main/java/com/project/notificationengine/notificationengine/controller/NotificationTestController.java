package com.project.notificationengine.notificationengine.controller;

import com.project.notificationengine.notificationengine.service.NotificationService;
import com.project.notificationengine.notificationengine.service.SubscriptionService;
import com.project.notificationengine.notificationengine.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class NotificationTestController {
    private final TopicService topicService;
    private final SubscriptionService subscriptionService;
    private final NotificationService notificationService;

    /**
     * API to create a new topic
     * @param topicName The name of the topic
     * @return ResponseEntity<Void>
     */
    @PostMapping("/createTopic")
    public ResponseEntity<Void> createTopic(@RequestParam String topicName) {
        log.info("Creating new topic: {}", topicName);
        topicService.createTopic(topicName);
        return ResponseEntity.ok().build();
    }

    /**
     * API to subscribe a client to a topic
     * @param topicName The name of the topic to subscribe to
     * @param subscriberUrl The URL that will receive notifications
     * @return ResponseEntity<Void>
     */
    @Operation(summary = "Subscribe to a topic", description = "Default subscriber URL: `http://localhost:8080/test/receive`")
    @PostMapping("/subscribe")
    public ResponseEntity<Void> subscribe(@RequestParam String topicName, @RequestParam String subscriberUrl) {
        log.info("📩 Subscribing client at {} to topic: {}", subscriberUrl, topicName);
        subscriptionService.subscribe(topicName, subscriberUrl);
        return ResponseEntity.ok().build();
    }

    /**
     * API to send a notification to all subscribers of a topic
     * @param topicName The topic name
     * @param subject The subject of the notification
     * @param message The content of the notification
     * @return ResponseEntity<Void>
     */
    @PostMapping("/sendEvent")
    public ResponseEntity<Void> sendEvent(@RequestParam String topicName, @RequestParam String subject, @RequestParam String message) {
        log.info("🚀 Sending event to topic {}: {}", topicName, message);
        notificationService.publish(topicName, subject, message);
        return ResponseEntity.ok().build();
    }

    private static boolean ready = false;
    @PostMapping("/simulate-ready-to-recieve")
    public ResponseEntity<Void> simulateReadyTorecieve(@RequestParam boolean readyToReceive) {
        ready = readyToReceive;
        return ResponseEntity.ok().build();
    }

    /**
     * API to simulate a subscriber receiving a notification
     * @param message The received notification content
     * @return ResponseEntity<Void>
     */
    @PostMapping("/receive")
    public ResponseEntity<Void> receiveNotification(@RequestBody String message) {
        if(ready){
            log.info("Received notification: {}", message);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.internalServerError().build();
        }

    }
}
