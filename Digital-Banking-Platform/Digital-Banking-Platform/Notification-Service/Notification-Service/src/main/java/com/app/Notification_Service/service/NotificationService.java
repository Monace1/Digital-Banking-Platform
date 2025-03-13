package com.app.Notification_Service.service;

import com.app.Notification_Service.model.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    public void sendNotification(Notification notification) {
        log.info("Sending notification: {}", notification);


        if (notification.getEmail() != null) {
            log.info("Sending Email to {}: {}", notification.getEmail(), notification.getMessage());
        }
        if (notification.getPhoneNumber() != null) {
            log.info("Sending SMS to {}: {}", notification.getPhoneNumber(), notification.getMessage());
        }
    }
}
