package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.entities.enums.TruckState;
import com.egovorushkin.logiweb.entities.enums.TruckStatus;
import com.egovorushkin.logiweb.services.api.CityService;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/trucks")
public class TruckController {

    private final TruckService truckService;
    private final CityService cityService;
    private final DriverService driverService;

    @Autowired
    public TruckController(TruckService truckService, CityService cityService, DriverService driverService) {
        this.truckService = truckService;
        this.cityService = cityService;
        this.driverService = driverService;
    }

    @GetMapping("/list")
    public String getAllTrucks(Model model) {
        model.addAttribute("trucks", truckService.getAllTrucks());
        return "manager/truck/list";
    }

    @GetMapping("/{id}")
    public String showTruck(@PathVariable("id") long id, Model model) throws Exception {
        model.addAttribute("truck", truckService.getTruckById(id));
        model.addAttribute("states", TruckState.values());
        model.addAttribute("statuses", TruckStatus.values());
        model.addAttribute("currentDrivers", truckService.findCurrentDriversByTruckId(id));
        model.addAttribute("numberOfDrivers", truckService.findCurrentDriversByTruckId(id).size());
        return "manager/truck/show";
    }

    @GetMapping("/create")
    public String showCreateTruckForm(Model model) {
        model.addAttribute("truck", new TruckDto());
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("states", TruckState.values());
        return "manager/truck/create";
    }

    @PostMapping("/save")
    public String createTruck(@ModelAttribute("truck") @Valid TruckDto truckDto,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("statuses", TruckStatus.values());
            model.addAttribute("cities", cityService.getAllCities());
            return "manager/truck/create";
        }
        truckService.createTruck(truckDto);
        return "redirect:/trucks/list";
    }

    @GetMapping("/edit")
    public String showEditTruckForm(@RequestParam("truckId") long id, Model model) throws Exception {
        model.addAttribute("truck", truckService.getTruckById(id));
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("states", TruckState.values());
        model.addAttribute("statuses", TruckStatus.values());
        model.addAttribute("currentDrivers", truckService.findCurrentDriversByTruckId(id));
        model.addAttribute("availableDrivers",
                truckService.findAvailableDriversByTruck(truckService.getTruckById(id)));
        return "manager/truck/edit";
    }

    @PostMapping("/update")
    public String updateTruck(@ModelAttribute("truck") @Valid TruckDto truckDto,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "manager/truck/edit";
        }
        truckService.updateTruck(truckDto);
        return "redirect:/trucks/list";
    }

    @GetMapping("/delete")
    public String deleteTruck(@RequestParam("truckId") long id) {
        truckService.deleteTruck(id);
        return "redirect:manager/truck/list";
    }

    @GetMapping("/bind-driver")
    public String bindDriverForTruck(@RequestParam("truckId") long truckId,
                                    @RequestParam("driverId") long driverId,
                                    RedirectAttributes redirectAttributes){
        TruckDto truck = truckService.getTruckById(truckId);
        DriverDto driver = driverService.getDriverById(driverId);
        if (truck.getCurrentDrivers().size() < truck.getTeamSize()) {
            driver.setTruck(truck);
            driverService.updateDriver(driver);
        }

        redirectAttributes.addAttribute("truckId", truckId);
        return "redirect:{truckId}";
    }

    @GetMapping("/unbind-driver")
    public String unbindDriverForTruck(@RequestParam("truckId") long truckId,
                                    @RequestParam("driverId") long driverId,
                                    RedirectAttributes redirectAttributes){
        DriverDto driver = driverService.getDriverById(driverId);
        driver.setTruck(null);
        driverService.updateDriver(driver);

        redirectAttributes.addAttribute("truckId", truckId);
        return "redirect:{truckId}";
    }

}
