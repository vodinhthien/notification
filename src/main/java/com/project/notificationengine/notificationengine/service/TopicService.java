package com.project.notificationengine.notificationengine.service;


import com.project.notificationengine.notificationengine.model.Subscription;
import com.project.notificationengine.notificationengine.model.Topic;
import com.project.notificationengine.notificationengine.repository.SubscriptionRepository;
import com.project.notificationengine.notificationengine.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;
    private final SubscriptionRepository subscriptionRepository;

    public void createTopic(String topicName) {
        if (!topicRepository.exists(topicName)) {
            Topic topic = new Topic();
            topic.setName(topicName);
            topicRepository.save(topic);
        }
    }

    public List<String> getAllTopics() {
        return topicRepository.findAll().stream()
                .map(Topic::getName)
                .collect(Collectors.toList());
    }

    public void deleteTopic(String topicName) {
        topicRepository.delete(topicName);
    }

    public List<Subscription> getSubscriptionsByTopic(String topicName) {
        return subscriptionRepository.findByTopic(topicName);
    }
}
