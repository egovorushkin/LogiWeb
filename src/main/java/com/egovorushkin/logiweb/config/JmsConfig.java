package com.egovorushkin.logiweb.config;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.Collections;

@Configuration
@EnableJms
public class JmsConfig {

    private static final String MESSAGE_BROKER_URL = "tcp://localhost:61616";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String TOPIC_NAME = "activemq/topic/ScoreboardTopic";

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(MESSAGE_BROKER_URL);
        connectionFactory.setUserName(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        connectionFactory.setTrustedPackages(Collections
                .singletonList("com.egovorushkin.logiweb"));
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate;
        jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory());
        jmsTemplate.setPubSubDomain(true);
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
        jmsTemplate.setDefaultDestinationName(TOPIC_NAME);
        return jmsTemplate;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

}
