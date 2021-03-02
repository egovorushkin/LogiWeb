package com.egovorushkin.logiweb.controllers;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Date;

/**
 * Controller used to showcase what happens when an exception is thrown
 */
@ControllerAdvice
@EnableWebMvc
public class ExceptionController {

    private static final Logger LOGGER =
            Logger.getLogger(ExceptionController.class.getName());

    @ExceptionHandler(value = Exception.class)
    public String handleError(HttpServletRequest request, Exception ex, Model model) {
        LOGGER.error("Request: " + request.getRequestURL() + " raised " + ex);

        model.addAttribute("errorMsg", "Application has encountered an error." +
                " Please contact support on...");
        return "error";
    }

    /**
     * Convert a predefined exception to an HTTP Status code
     */
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity " +
            "violation")
    @ExceptionHandler(DataIntegrityViolationException.class)
    // 409
    public String conflict(Model model) {
        LOGGER.error("Request raised a DataIntegrityViolationException");

        model.addAttribute("errorMsg", "Data integrity violation");

        return "error";
    }

    /**
     * Convert a predefined exception to an HTTP Status code and specify the
     * name of a specific view that will be used to display the error.
     *
     * @return Exception view.
     */
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public String databaseError(Exception ex, Model model) {
        LOGGER.error("Request raised " + ex.getClass().getSimpleName());

        return "databaseError";
    }
}
