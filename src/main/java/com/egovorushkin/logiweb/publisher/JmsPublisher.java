package com.egovorushkin.logiweb.publisher;

import java.io.Serializable;

public interface JmsPublisher {

    void send ();

    void sendMessage(final String message);

    void sendMessage(final Serializable message);

}
