package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dto.CargoDto;
import com.egovorushkin.logiweb.entities.enums.CargoStatus;
import com.egovorushkin.logiweb.services.api.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/cargoes")
public class CargoController {

    private final CargoService cargoService;

    @Autowired
    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping("/list")
    public String showAllCargoes(Model model) {
        model.addAttribute("cargoes", cargoService.getAllCargoes());
        return "manager/cargo/list";
    }

    @GetMapping("/{id}")
    public String showCargo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cargo", cargoService.getCargoById(id));
        model.addAttribute("statuses", CargoStatus.values());
        return "manager/cargo/show";
    }

    @GetMapping("/create")
    public String showCreateCargoForm(Model model) {
        model.addAttribute("cargo", new CargoDto());
        return "manager/cargo/create";
    }

    @PostMapping("/save")
    public String createCargo(@ModelAttribute("cargo") @Valid CargoDto cargoDto,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "manager/cargo/create";
        }
        cargoService.createCargo(cargoDto);
        return "redirect:/cargoes/list";
    }

    @GetMapping("/edit")
    public String showEditCargoForm(@RequestParam("cargoId") Long id, Model model) {
        model.addAttribute("cargo", cargoService.getCargoById(id));
        model.addAttribute("statuses", CargoStatus.values());
        return "manager/cargo/edit";
    }

    @PostMapping("/update")
    public String updateCargo(@ModelAttribute("cargo") @Valid CargoDto cargoDto,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "manager/cargo/edit";
        }
        cargoService.updateCargo(cargoDto);
        return "redirect:/cargoes/list";
    }

    @GetMapping("/delete")
    public String deleteCargo(@RequestParam("cargoId") Long id) {
        cargoService.deleteCargo(id);
        return "redirect:/cargoes/list";
    }
}
