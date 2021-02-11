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

    @GetMapping("/update-status")
    public String updateStatusOfDriver(@RequestParam("userStatus") DriverStatus userStatus) {
        driverService.updateStatus(userStatus);
        return "redirect:/user/info";
    }

    @GetMapping("/update-state")
    public String updateStateOfDriver(@RequestParam("userState") boolean userState) {
        driverService.updateState(userState);
        return "redirect:/user/info";
    }

    @PostMapping("/update-order-status")
    public String updateStatusOfOrder(@ModelAttribute("user") OrderDto orderDto) {
        orderService.updateStatusOfOrder(orderDto);
        return "redirect:/user/orders";
    }

    @GetMapping("/orders")
    public String showOrders(Model model) {
        DriverDto user = driverService.getAuthorizedDriverByUsername();
        model.addAttribute("orders", orderService.
                findCurrentOrdersForTruck(user.getTruck()));
        return "/user/orders";
    }
}
