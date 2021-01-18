package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.entities.enums.DriverStatus;
import com.egovorushkin.logiweb.services.api.CityService;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;
    private final TruckService truckService;
    private final CityService cityService;

    @Autowired
    public DriverController(DriverService driverService,
                            TruckService truckService,
                            CityService cityService) {
        this.driverService = driverService;
        this.truckService = truckService;
        this.cityService = cityService;
    }

    @GetMapping("/list")
    public String showAllDrivers(Model model) {
        model.addAttribute("drivers", driverService.getAllDrivers());
        return "manager/driver/list";
    }

    @GetMapping("/{id}")
    public String showDriver(@PathVariable("id") long id, Model model) {
        model.addAttribute("driver", driverService.getDriverById(id));
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("statuses", DriverStatus.values());
        model.addAttribute("trucks", truckService.getAllTrucks());
        return "manager/driver/show";
    }

    @GetMapping("/create")
    public String showCreateDriverForm(Model model) {
        model.addAttribute("driver", new DriverDto());
        model.addAttribute("trucks", truckService.getAllTrucks());
        model.addAttribute("cities", cityService.getAllCities());
        return "manager/driver/create";
    }

    @PostMapping("/save")
    public String saveDriver(@ModelAttribute("driver") @Valid DriverDto driverDto,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("trucks", truckService.getAllTrucks());
            model.addAttribute("cities", cityService.getAllCities());
            return "manager/driver/create";
        }
        driverService.createDriver(driverDto);
        return "redirect:/drivers/list";
    }

    @GetMapping("/edit")
    public String showEditDriverForm(@RequestParam("driverId") long id, Model model) {
        model.addAttribute("driver", driverService.getDriverById(id));
        model.addAttribute("statuses", DriverStatus.values());
        model.addAttribute("trucks", truckService.getAllTrucks());
        model.addAttribute("cities", cityService.getAllCities());
        return "manager/driver/edit";
    }

    @PostMapping("/update")
    public String updateDriver(@ModelAttribute("driver") @Valid DriverDto driverDto,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "manager/driver/edit";
        }
        driverService.updateDriver(driverDto);
        return "redirect:/drivers/list";
    }

    @GetMapping("/delete")
    public String deleteDriver(@RequestParam("driverId") long id) {
        driverService.deleteDriver(id);
        return "redirect:/drivers/list";
    }

//    @GetMapping("/updateTruckForDriver")
//    public String updateTruckForDriver(@RequestParam("availableDriverId")
//    long driverId, @RequestParam("truckId") long truckId){
//        DriverDto driverDto = driverService.getDriverById(driverId);
//        driverDto.setTruck(truckService.getTruckById(truckId));
//        driverService.updateDriver(driverDto);
//        return "manager/driver/edit";
//    }
}
