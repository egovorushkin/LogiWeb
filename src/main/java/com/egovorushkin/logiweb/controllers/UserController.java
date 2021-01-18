package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.services.api.DriverService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/driver")
public class UserController {

    private final DriverService driverService;

    public UserController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/info")
    public String showPersonalInfo(Model model) {
        model.addAttribute("user", driverService.getDriverByUsername());
        return "/driver/profile";
    }

    @GetMapping("/orders")
    public String showOrders(Model model) {
        return "/driver/orders";
    }
}
