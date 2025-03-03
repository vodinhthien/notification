package com.project.notificationengine.notificationengine.repository;

import com.project.notificationengine.notificationengine.model.Topic;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class TopicRepository {
    private final List<Topic> topics = new CopyOnWriteArrayList<>();

    public void save(Topic topic) {
        topics.add(topic);
    }

    public List<Topic> findAll() {
        return topics;
    }

    public boolean exists(String name) {
        return topics.stream().anyMatch(topic -> topic.getName().equals(name));
    }

    public void delete(String name) {
        topics.removeIf(topic -> topic.getName().equals(name));
    }
}
