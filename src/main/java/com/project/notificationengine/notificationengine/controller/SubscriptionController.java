package com.project.notificationengine.notificationengine.controller;

import com.project.notificationengine.notificationengine.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    @Autowired
    SubscriptionService subscriptionService;

    @Operation(summary = "Subcribe topic", description = "Default subscriberUrl: `http://localhost:8080/test/receive`")
    @PostMapping
    public void subscribe(@RequestParam String topic, @RequestParam String url) {
        subscriptionService.subscribe(topic, url);
    }
}
