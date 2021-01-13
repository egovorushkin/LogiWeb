package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.entities.Truck;
import com.egovorushkin.logiweb.entities.enums.TruckState;
import com.egovorushkin.logiweb.entities.enums.TruckStatus;
import com.egovorushkin.logiweb.services.api.CityService;
import com.egovorushkin.logiweb.services.api.TruckService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/trucks")
public class TruckController {

    private static final Logger logger = Logger.getLogger(TruckController.class.getName());

    private final TruckService truckService;
    private final CityService cityService;

    @Autowired
    public TruckController(TruckService truckService, CityService cityService) {
        this.truckService = truckService;
        this.cityService = cityService;
    }

    @GetMapping(value = "/list")
    public String showAllTrucks(Model model) {

        if (logger.isDebugEnabled()){
            logger.debug("showAllTrucks is executed");
        }

        model.addAttribute("trucks", truckService.listAll());
        return "manager/truck/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) throws Exception {

        if (logger.isDebugEnabled()){
            logger.debug("show is executed");
        }

        model.addAttribute("truck", truckService.showTruck(id));
        model.addAttribute("states", TruckState.values());
        model.addAttribute("currentDrivers", truckService.findCurrentDrivers(id));
        model.addAttribute("numberOfDrivers", truckService.findCurrentDrivers(id).size());
        return "manager/truck/show";
    }

    @GetMapping(value = "/create")
    public String createTruckForm(Model model) {

        if (logger.isDebugEnabled()){
            logger.debug("createTruckForm is executed");
        }

        model.addAttribute("truck", new Truck());
        model.addAttribute("cities", cityService.listAll());
        return "manager/truck/create";
    }

    @PostMapping(value = "/save")
    public String saveTruck(@ModelAttribute("truck") @Valid Truck truck,
                            BindingResult bindingResult, Model model) {

        if (logger.isDebugEnabled()){
            logger.debug("saveTruck is executed");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("cities", cityService.listAll());
            return "manager/truck/create";
        }
        truckService.saveTruck(truck);
        return "redirect:/trucks/list";
    }

    @GetMapping(value = "/edit")
    public String editTruckForm(@RequestParam("truckId") int id, Model model) {

        if (logger.isDebugEnabled()){
            logger.debug("editTruckForm is executed");
        }

        Truck truck = truckService.getTruckById(id);
        model.addAttribute("truck", truck);
        model.addAttribute("cities", cityService.listAll());
        model.addAttribute("states", TruckState.values());
        model.addAttribute("statuses", TruckStatus.values());
        return "manager/truck/edit";
    }

    @PostMapping("/update")
    public String updateTruck(@ModelAttribute("truck") @Valid Truck truck,
                              BindingResult bindingResult) {

        if (logger.isDebugEnabled()){
            logger.debug("updateTruck is executed");
        }

        if (bindingResult.hasErrors()) {
            return "manager/truck/edit";
        }
        truckService.update(truck);
        return "redirect:/trucks/list";
    }

    @GetMapping(value = "/delete")
    public String deleteTruck(@RequestParam("truckId") int id) {

        if (logger.isDebugEnabled()){
            logger.debug("deleteTruck is executed");
        }

        truckService.delete(id);
        return "redirect:/trucks/list";
    }
}
