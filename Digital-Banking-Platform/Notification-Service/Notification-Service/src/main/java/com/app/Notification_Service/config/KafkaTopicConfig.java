package com.app.Notification_Service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic transactionTopic() {
        return new NewTopic("transaction-events", 1, (short) 1);
    }
}

