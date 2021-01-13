package com.egovorushkin.logiweb.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @GetMapping(value = "/login")
    public String loginPage() {

        if (logger.isDebugEnabled()){
            logger.debug("loginPage is executed");
        }

        return "login";
    }

    @GetMapping(value = "/access-denied")
    public String accessDenied() {

        if (logger.isDebugEnabled()){
            logger.debug("accessDenied is executed");
        }

        return "access-denied";
    }
}
