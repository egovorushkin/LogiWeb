package com.egovorushkin.logiweb.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Implements {@link JmsPublisher}
 * Implement methods for sending message to message broker
 */
@Component
public class JmsPublisherImpl implements JmsPublisher {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsPublisherImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void send() {
        sendMessage("update");
    }

    @Override
    public void sendMessage(final String message) {
        jmsTemplate.send(session -> session.createTextMessage(message));
    }

    @Override
    public void sendMessage(final Serializable message) {
        jmsTemplate.send(session -> session.createObjectMessage(message));
    }

}
