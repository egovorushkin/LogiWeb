package com.egovorushkin.logiweb.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LoginController {

    private static final Logger LOGGER = Logger
            .getLogger(LoginController.class.getName());

    @GetMapping("/login")
    public String loginPage() {

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("loginPage is executed");
        }

        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("accessDenied is executed");
        }

        return "access-denied";
    }
}
