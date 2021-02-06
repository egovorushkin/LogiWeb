package com.egovorushkin.logiweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/admin")
    public String showHomeForAdmin() {

        return "/manager/manager-main-page";
    }

    @GetMapping("/driver")
    public String showHomeForUser() {

        return "/user/user-main-page";
    }
}
