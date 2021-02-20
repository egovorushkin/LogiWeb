package com.egovorushkin.logiweb.controllers;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Creates and sends the HTTP requests which are used
 * to write unit tests for controllers methods which
 * provide CRUD operations for logiweb entities.
 */

public class CargoRequestBuilder {

    private final MockMvc mockMvc;

    public CargoRequestBuilder(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    /**
     * Creates and sends the HTTP requests which gets the
     * HTML document that displays the information of all logiweb entities.
     * @return MockMvc
     * @throws Exception ex
     */

    ResultActions findAll() throws Exception {
        return mockMvc.perform(get("/cargoes/list"));
    }

    ResultActions create() throws Exception {
        return mockMvc.perform(get("/cargoes/create"));
    }

    /**
     * Creates and sends the HTTP request which gets the
     * HTML document that displays the information of the
     * requested logiweb entity.
     * @param id    The id of the requested logiweb entity.
     * @return MockMvc
     * @throws Exception ex
     */
    ResultActions findById(Long id) throws Exception {
        return mockMvc.perform(get("/cargoes/{id}", id));
    }
}
