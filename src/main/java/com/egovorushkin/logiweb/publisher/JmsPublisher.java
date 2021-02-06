package com.egovorushkin.logiweb.publisher;

import com.egovorushkin.logiweb.dto.OrderDto;

public interface JmsPublisher {

    void send (String message);

    void send (OrderDto orderDto);
}
