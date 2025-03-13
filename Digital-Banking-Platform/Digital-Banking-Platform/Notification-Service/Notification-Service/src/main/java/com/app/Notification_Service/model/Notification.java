package com.app.Notification_Service.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    private String email;
    private String phoneNumber;
    private String message;
}

