package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/driver")
public class UserController {

    private final DriverService driverService;
    private final OrderService orderService;

    public UserController(DriverService driverService, OrderService orderService) {
        this.driverService = driverService;
        this.orderService = orderService;
    }

    @GetMapping("/info")
    public String showPersonalInfo(Model model) {
        model.addAttribute("user", driverService.getDriverByUsername());
        return "/driver/profile";
    }

//    @GetMapping("/edit")
//    public String showEditDriverForm(@RequestParam("userId") long id, Model model) {
//        model.addAttribute("user", driverService.getDriverById(id));
//        model.addAttribute("statuses", DriverStatus.values());
//        return "driver/edit-profile";
//    }

    @PostMapping("/update-status")
    public String updateDriver(@ModelAttribute("user") @Valid DriverDto driverDto,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "driver/profile";
        }

        driverService.updateDriver(driverDto);
        return "redirect:/driver/info";
    }


    @GetMapping("/orders")
    public String showOrders(Model model) {
        DriverDto user = driverService.getDriverByUsername();
        model.addAttribute("orders", orderService.
                findCurrentOrdersForTruck(user.getTruck().getId()));
        return "/driver/orders";
    }
}
