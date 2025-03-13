package com.app.Notification_Service.service;


import com.app.Notification_Service.model.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "transaction-events", groupId = "notification-group")
    public void listen(Notification notification) {
        log.info("Received Notification: {}", notification);
        notificationService.sendNotification(notification);
    }
}

