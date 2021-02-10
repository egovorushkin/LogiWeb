package com.egovorushkin.logiweb.publisher;

import com.egovorushkin.logiweb.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

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

//    @Override
//    public void send(OrderDto orderDto) {
//        jmsTemplate.convertAndSend(orderDto);
//    }
}
