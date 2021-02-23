package com.egovorushkin.logiweb.publisher;

import java.io.Serializable;

/**
 * This interface represent publisher for
 * sending messages to message broker
 */
public interface JmsPublisher {

    void send();

    void sendMessage(final String message);

    void sendMessage(final Serializable message);

}
