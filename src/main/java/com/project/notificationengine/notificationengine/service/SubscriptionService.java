package com.project.notificationengine.notificationengine.service;

import com.project.notificationengine.notificationengine.model.Subscription;
import com.project.notificationengine.notificationengine.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    /**
     * Subscribes a client to a topic.
     * If the client has already subscribed, it will not be added again.
     * @param topic The name of the topic.
     * @param url The subscriber's URL to receive notifications.
     */
    public void subscribe(String topic, String url) {
        if (subscriptionRepository.existsByTopicAndUrl(topic, url)) {
            log.warn("Client at {} is already subscribed to topic {}. Skipping duplicate subscription!", url, topic);
            return;
        }
        Subscription subscription = new Subscription();
        subscription.setTopic(topic);
        subscription.setUrl(url);
        subscriptionRepository.save(subscription);
        log.info("Successfully subscribed client at {} to topic {}", url, topic);
    }
}
