package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    private DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping(value = "/list")
    public String showAllDrivers(Model model) {
        List<Driver> drivers = driverService.listAll();
        model.addAttribute("drivers", drivers);
        return "driver/list";
    }

//    @GetMapping("/{id}")
//    public String show(@PathVariable("id") int id, Model model) {
//        model.addAttribute("truck", truckService.showTruck(id));
//        return "truck/show";
//    }

    @GetMapping(value = "/create")
    public String createDriverForm(Model model) {
        model.addAttribute("driver", new Driver());
        return "/driver/create";
    }

    @PostMapping(value = "/save")
    public String saveDriver(@ModelAttribute("driver") @Valid Driver driver, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "driver/create";
        }
        driverService.saveDriver(driver);
        return "redirect:/drivers/list";
    }

    @GetMapping(value = "/edit")
    public String editDriverForm(@RequestParam("driverId") int id, Model model) {
        Driver driver = driverService.getDriverById(id);
        model.addAttribute("driver", driver);
        return "driver/edit";
    }

    @PostMapping("/update")
    public String updateDriver(@ModelAttribute("driver") @Valid Driver driver, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "driver/edit";
        }
        driverService.update(driver);
        return "redirect:/drivers/list";
    }

    @GetMapping(value = "/delete")
    public String deleteDriver(@RequestParam("driverId") int id) {
        driverService.delete(id);
        return "redirect:/drivers/list";
    }
}
