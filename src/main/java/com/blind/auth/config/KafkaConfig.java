package com.blind.auth.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.topic.name.email}")
    private String topicEmailName;

    @Bean
    public NewTopic emailTopic(){
        return TopicBuilder.name(topicEmailName)
                .build();
    }
}
