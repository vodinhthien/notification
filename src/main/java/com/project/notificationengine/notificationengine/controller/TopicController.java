package com.project.notificationengine.notificationengine.controller;

import com.project.notificationengine.notificationengine.model.Subscription;
import com.project.notificationengine.notificationengine.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;

    /**
     * API to create a new topic
     * @param topicName The name of the topic
     * @return ResponseEntity<Void>
     */
    @PostMapping
    public ResponseEntity<Void> createTopic(@RequestParam String topicName) {
        topicService.createTopic(topicName);
        return ResponseEntity.ok().build();
    }

    /**
     * API to retrieve a list of all topics
     * @return List of topic names
     */
    @GetMapping
    public ResponseEntity<List<String>> listTopics() {
        return ResponseEntity.ok(topicService.getAllTopics());
    }

    /**
     * API to delete a topic by name
     * @param topicName The name of the topic
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/{topicName}")
    public ResponseEntity<Void> deleteTopic(@PathVariable String topicName) {
        topicService.deleteTopic(topicName);
        return ResponseEntity.ok().build();
    }

    /**
     * API to retrieve the list of subscriptions for a specific topic
     * @param topicName The name of the topic
     * @return List of subscriptions
     */
    @GetMapping("/{topicName}/subscriptions")
    public ResponseEntity<List<Subscription>> getSubscriptionsByTopic(@PathVariable String topicName) {
        return ResponseEntity.ok(topicService.getSubscriptionsByTopic(topicName));
    }
}
