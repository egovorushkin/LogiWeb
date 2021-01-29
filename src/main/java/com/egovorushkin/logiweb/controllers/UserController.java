package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.OrderDto;
import com.egovorushkin.logiweb.entities.enums.DriverStatus;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("colleague", driverService.findColleagueAuthorizedDriverByUsername());
        model.addAttribute("statuses", DriverStatus.values());
        return "/user/profile";
    }

    @PostMapping("/update-status")
    public String updateStatusOfDriver(@ModelAttribute("user") DriverDto driverDto) {
        driverService.updateStatus(driverDto);
        return "redirect:/user/info";
    }

    @PostMapping("/update-state")
    public String updateStateOfDriver(@ModelAttribute("user") DriverDto driverDto) {
        driverService.updateState(driverDto);
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
