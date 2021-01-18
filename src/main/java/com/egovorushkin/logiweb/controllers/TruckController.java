package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.entities.enums.TruckState;
import com.egovorushkin.logiweb.entities.enums.TruckStatus;
import com.egovorushkin.logiweb.services.api.CityService;
import com.egovorushkin.logiweb.services.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/trucks")
public class TruckController {

    private final TruckService truckService;
    private final CityService cityService;


    @Autowired
    public TruckController(TruckService truckService, CityService cityService) {
        this.truckService = truckService;
        this.cityService = cityService;
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
        model.addAttribute("currentDrivers", truckService.findCurrentDriversByTruckId(id));
        model.addAttribute("numberOfDrivers", truckService.findCurrentDriversByTruckId(id).size());
        return "manager/truck/show";
    }

    @GetMapping("/create")
    public String showCreateTruckForm(Model model) {
        model.addAttribute("truck", new TruckDto());
        model.addAttribute("cities", cityService.getAllCities());
        return "manager/truck/create";
    }

    @PostMapping("/save")
    public String createTruck(@ModelAttribute("truck") @Valid TruckDto truckDto,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
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
        return "redirect:/trucks/list";
    }
}
