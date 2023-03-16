package com.blind.auth.producer;

import com.blind.common.kafka.event.EmailVerificationEvent;

public interface EmailVerificationProducer {

    void sendMessage(EmailVerificationEvent emailVerificationEvent);
}
