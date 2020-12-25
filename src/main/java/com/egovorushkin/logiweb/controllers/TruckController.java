package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.entities.Truck;
import com.egovorushkin.logiweb.entities.status.TruckStatus;
import com.egovorushkin.logiweb.services.CityService;
import com.egovorushkin.logiweb.services.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/trucks")
public class TruckController {

    private TruckService truckService;

    private CityService cityService;

    @Autowired
    public TruckController(TruckService truckService, CityService cityService) {

        this.truckService = truckService;
        this.cityService = cityService;
    }

    @GetMapping(value = "/list")
    public String showAllTrucks(Model model) {
        List<Truck> trucks = truckService.listAll();
        model.addAttribute("trucks", trucks);
        return "truck/list";
    }

//    @GetMapping("/{id}")
//    public String show(@PathVariable("id") int id, Model model) {
//        model.addAttribute("truck", truckService.showTruck(id));
//        return "truck/show";
//    }

    @GetMapping(value = "/create")
    public String createTruckForm(Model model) {
        model.addAttribute("truck", new Truck());
        return "/truck/create";
    }

    @PostMapping(value = "/save")
    public String saveTruck(@ModelAttribute("truck") @Valid Truck truck, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "truck/create";
        }
        truckService.saveTruck(truck);
        return "redirect:/trucks/list";
    }

    @GetMapping(value = "/edit")
    public String editTruckForm(@RequestParam("truckId") int id, Model model) {
        Truck truck = truckService.getTruckById(id);
        model.addAttribute("truck", truck);
        model.addAttribute("cities", cityService.listAll());
        model.addAttribute("statuses", TruckStatus.values());
        return "truck/edit";
    }

    @PostMapping("/update")
    public String updateTruck(@ModelAttribute("truck") @Valid Truck truck, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "truck/edit";
        }
        truckService.update(truck);
        return "redirect:/trucks/list";
    }

    @GetMapping(value = "/delete")
    public String deleteTruck(@RequestParam("truckId") int id) {
        truckService.delete(id);
        return "redirect:/trucks/list";
    }
}
