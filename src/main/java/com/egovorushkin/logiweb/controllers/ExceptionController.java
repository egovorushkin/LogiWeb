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
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Date;

@ControllerAdvice
@EnableWebMvc
public class ExceptionController {

    private static final Logger LOGGER =
            Logger.getLogger(ExceptionController.class.getName());

//    @ExceptionHandler(value = Exception.class)
//    public String handleError(HttpServletRequest request, Exception ex, Model model) {
//        LOGGER.error("Request: " + request.getRequestURL() + " raised " + ex);
//
//        model.addAttribute("exception", ex);
//        model.addAttribute("url", request.getRequestURL());
//        model.addAttribute("timestamp", new Date().toString());
//
//        return "error";
//    }

    /**
     * Convert a predefined exception to an HTTP Status code
     */
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity " +
            "voilation")
    @ExceptionHandler(DataIntegrityViolationException.class)
    // 409
    public void confilct() {
        LOGGER.error("Request raised a DataIntegrityViolationException");
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

        model.addAttribute("exception", ex);
        model.addAttribute("timestamp", new Date().toString());
        return "databaseError";
    }
}
