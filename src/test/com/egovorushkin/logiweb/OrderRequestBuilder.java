package com.egovorushkin.logiweb;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Creates and sends the HTTP requests which are used
 * to write unit tests for controllers methods which
 * provide CRUD operations for logiweb entities.
 */

public class OrderRequestBuilder {

    private final MockMvc mockMvc;

    public OrderRequestBuilder(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    /**
     * Creates and sends the HTTP requests which gets the
     * HTML document that displays the information of all logiweb entities.
     * @return
     * @throws Exception
     */

    ResultActions findAll() throws Exception {
        return mockMvc.perform(get("/orders/list"));
    }

    /**
     * Creates and sends the HTTP request which gets the
     * HTML document that displays the information of the
     * requested logiweb entity.
     * @param id    The id of the requested logiweb entity.
     * @return
     * @throws Exception
     */
    ResultActions findById(Long id) throws Exception {
        return mockMvc.perform(get("/orders/{id}", id));
    }
}
