package com.blind.auth.producer.impl;

import com.blind.auth.producer.EmailVerificationProducer;
import com.blind.common.kafka.event.EmailVerificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailVerificationProducerImpl implements EmailVerificationProducer {

    private final NewTopic emailTopic;

    private final KafkaTemplate<String, EmailVerificationEvent> kafkaTemplate;

    @Override
    public void sendMessage(EmailVerificationEvent emailVerificationEvent) {
        log.info(String.format("Email verification info ===> %s", emailVerificationEvent.toString()));

        Message<EmailVerificationEvent> message = MessageBuilder.withPayload(emailVerificationEvent)
                .setHeader(KafkaHeaders.TOPIC, emailTopic.name())
                .build();

        kafkaTemplate.send(message);
    }
}
