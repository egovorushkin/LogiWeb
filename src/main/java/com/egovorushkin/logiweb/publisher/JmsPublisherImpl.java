package com.egovorushkin.logiweb.publisher;

import org.apache.log4j.Logger;
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

    private static final Logger LOGGER =
            Logger.getLogger(JmsPublisherImpl.class.getName());

    private static final String MSG =
            "Message broker is not running. Message not sent.";

    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsPublisherImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void send() {
        try {
            sendMessage("update");
        } catch (Exception ex) {
            LOGGER.error(MSG);
        }
    }

    @Override
    public void sendMessage(final String message) {
        try {
            jmsTemplate.send(session -> session.createTextMessage(message));
        } catch (Exception ex) {
            LOGGER.error(MSG);
        }
    }

    @Override
    public void sendMessage(final Serializable message) {
        try {
            jmsTemplate.send(session -> session.createObjectMessage(message));
        } catch (Exception ex) {
            LOGGER.error(MSG);
        }
    }

}
