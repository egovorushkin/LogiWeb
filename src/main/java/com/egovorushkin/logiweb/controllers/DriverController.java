package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.enums.DriverStatus;
import com.egovorushkin.logiweb.services.api.CityService;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.TruckService;
import org.apache.log4j.Logger;
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

    private static final Logger logger = Logger.getLogger(DriverController.class.getName());

    private final DriverService driverService;
    private final TruckService truckService;
    private final CityService cityService;

    @Autowired
    public DriverController(DriverService driverService, TruckService truckService,
                            CityService cityService) {
        this.driverService = driverService;
        this.truckService = truckService;
        this.cityService = cityService;
    }

    @GetMapping(value = "/list")
    public String showAllDrivers(Model model) {

        if (logger.isDebugEnabled()){
            logger.debug("showAllDrivers is executed");
        }

        List<Driver> drivers = driverService.listAll();
        model.addAttribute("drivers", drivers);
        return "manager/driver/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {

        if (logger.isDebugEnabled()){
            logger.debug("show is executed");
        }

        model.addAttribute("driver", driverService.showDriver(id));
        model.addAttribute("cities", cityService.listAll());
        model.addAttribute("statuses", DriverStatus.values());
        model.addAttribute("trucks", truckService.listAll());
        return "manager/driver/show";
    }

    @GetMapping(value = "/create")
    public String createDriverForm(Model model) {

        if (logger.isDebugEnabled()){
            logger.debug("createDriverForm is executed");
        }

        model.addAttribute("driver", new Driver());
        model.addAttribute("trucks", truckService.listAll());
        model.addAttribute("cities", cityService.listAll());
        return "manager/driver/create";
    }

    @PostMapping(value = "/save")
    public String saveDriver(@ModelAttribute("driver") @Valid Driver driver,
                             BindingResult bindingResult, Model model) {

        if (logger.isDebugEnabled()){
            logger.debug("saveDriver is executed");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("trucks", truckService.listAll());
            model.addAttribute("cities", cityService.listAll());
            return "manager/driver/create";
        }
        driverService.saveDriver(driver);
        return "redirect:/drivers/list";
    }

    @GetMapping(value = "/edit")
    public String editDriverForm(@RequestParam("driverId") int id, Model model) {

        if (logger.isDebugEnabled()){
            logger.debug("editDriverForm is executed");
        }

        Driver driver = driverService.getDriverById(id);
        model.addAttribute("driver", driver);
        model.addAttribute("statuses", DriverStatus.values());
        model.addAttribute("trucks", truckService.listAll());
        model.addAttribute("cities", cityService.listAll());
        return "manager/driver/edit";
    }

    @PostMapping("/update")
    public String updateDriver(@ModelAttribute("driver") @Valid Driver driver,
                               BindingResult bindingResult) {

        if (logger.isDebugEnabled()){
            logger.debug("updateDriver is executed");
        }

        if (bindingResult.hasErrors()) {
            return "manager/driver/edit";
        }
        driverService.update(driver);
        return "redirect:/drivers/list";
    }

    @GetMapping(value = "/delete")
    public String deleteDriver(@RequestParam("driverId") int id) {

        if (logger.isDebugEnabled()){
            logger.debug("deleteDriver is executed");
        }

        driverService.delete(id);
        return "redirect:/drivers/list";
    }
}
