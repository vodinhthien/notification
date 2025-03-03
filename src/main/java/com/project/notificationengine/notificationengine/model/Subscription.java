package com.project.notificationengine.notificationengine.model;

import lombok.Data;
import java.util.UUID;

@Data
public class Subscription {
    public String id = UUID.randomUUID().toString();
    public String topic;
    public String url;
}
