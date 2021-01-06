package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.entities.WaypointList;
import com.egovorushkin.logiweb.entities.status.WaypointListStatus;
import com.egovorushkin.logiweb.services.api.CargoService;
import com.egovorushkin.logiweb.services.api.CityService;
import com.egovorushkin.logiweb.services.api.WaypointListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/waypoint-lists")
public class WaypointListController {

    private final WaypointListService waypointListService;

    private final CityService cityService;

    private CargoService cargoService;

    @Autowired
    public WaypointListController(WaypointListService waypointListService, CityService cityService, CargoService cargoService) {

        this.waypointListService = waypointListService;
        this.cityService = cityService;
        this.cargoService = cargoService;
    }

    @GetMapping(value = "/list")
    public String showAllWaypointLists(Model model) {
        List<WaypointList> waypointLists = waypointListService.listAll();
        model.addAttribute("waypointLists", waypointLists);
        return "waypoint-list/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("waypointList", waypointListService.showWaypointList(id));
        model.addAttribute("cities", cityService.listAll());
        model.addAttribute("cargoes", cargoService.listAll());
        model.addAttribute("statuses", WaypointListStatus.values());
        return "waypoint-list/show";
    }

    @GetMapping(value = "/create")
    public String createWaypointListForm(Model model) {
        model.addAttribute("waypointList", new WaypointList());
        model.addAttribute("cities", cityService.listAll());
        model.addAttribute("cargoes", cargoService.listAll());
        return "/waypoint-list/create";
    }

    @PostMapping(value = "/save")
    public String saveWaypointList(@ModelAttribute("waypointList") @Valid WaypointList waypointList, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("cities", cityService.listAll());
            model.addAttribute("cargoes", cargoService.listAll());
            return "waypoint-list/create";
        }
        waypointListService.saveWaypointList(waypointList);
        return "redirect:/waypoint-lists/list";
    }

    @GetMapping(value = "/edit")
    public String editWaypointListForm(@RequestParam("waypointListId") int id, Model model) {
        WaypointList waypointList = waypointListService.getWaypointListById(id);
        model.addAttribute("waypointList", waypointList);
        model.addAttribute("cities", cityService.listAll());
        model.addAttribute("cargoes", cargoService.listAll());
        model.addAttribute("statuses", WaypointListStatus.values());
        return "waypoint-list/edit";
    }

    @PostMapping("/update")
    public String updateWaypointList(@ModelAttribute("waypointList") @Valid WaypointList waypointList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "waypoint-list/edit";
        }
        waypointListService.update(waypointList);
        return "redirect:/waypoint-lists/list";
    }

    @GetMapping(value = "/delete")
    public String deleteWaypointList(@RequestParam("waypointListId") int id) {
        waypointListService.delete(id);
        return "redirect:/waypoint-lists/list";
    }
}
