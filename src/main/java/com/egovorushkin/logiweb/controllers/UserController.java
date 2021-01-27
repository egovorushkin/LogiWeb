package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.OrderDto;
import com.egovorushkin.logiweb.entities.enums.DriverStatus;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final DriverService driverService;
    private final OrderService orderService;

    public UserController(DriverService driverService, OrderService orderService) {
        this.driverService = driverService;
        this.orderService = orderService;
    }

    @GetMapping("/info")
    public String showPersonalInfo(Model model) {
        model.addAttribute("user", driverService.getAuthorizedDriverByUsername());
        model.addAttribute("colleagues", driverService.findColleaguesAuthorizedDriverByUsername());
        model.addAttribute("statuses", DriverStatus.values());
        return "/user/profile";
    }

    @PostMapping("/update-status")
    public String updateStatusOfUser(@ModelAttribute("user") DriverDto driverDto) {
        driverService.mergeWithExistingAndUpdate(driverDto);
        return "redirect:/user/info";
    }

    @PostMapping("/update-order-status")
    public String updateStatusOfUser(@ModelAttribute("user") OrderDto orderDto) {
        orderService.mergeWithExistingAndUpdate(orderDto);
        return "redirect:/user/orders";
    }


    @GetMapping("/orders")
    public String showOrders(Model model) {
        DriverDto user = driverService.getAuthorizedDriverByUsername();
        model.addAttribute("orders", orderService.
                findCurrentOrdersForTruck(user.getTruck().getId()));
        return "/user/orders";
    }
}
