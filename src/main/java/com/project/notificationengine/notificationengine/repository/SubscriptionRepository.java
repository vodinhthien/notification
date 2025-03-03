package com.project.notificationengine.notificationengine.repository;


import com.project.notificationengine.notificationengine.model.Subscription;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@Repository
public class SubscriptionRepository {
    private final List<Subscription> subscriptions = new CopyOnWriteArrayList<>();

    public void save(Subscription subscription) {
        subscriptions.add(subscription);
    }

    public List<Subscription> findByTopic(String topic) {
        return subscriptions.stream()
                .filter(sub -> sub.getTopic().equals(topic))
                .toList();
    }

    public List<Subscription> findAll() {
        return subscriptions;
    }
    public boolean existsByTopicAndUrl(String topic, String url) {
        return subscriptions.stream()
                .anyMatch(sub -> sub.getTopic().equals(topic) && sub.getUrl().equals(url));
    }
}

