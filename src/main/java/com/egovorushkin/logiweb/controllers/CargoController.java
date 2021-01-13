package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.entities.Cargo;
import com.egovorushkin.logiweb.entities.enums.CargoStatus;
import com.egovorushkin.logiweb.services.api.CargoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/cargoes")
public class CargoController {

    private static final Logger logger = Logger.getLogger(CargoController.class.getName());


    private final CargoService cargoService;

    @Autowired
    public CargoController(CargoService cargoService) {

        this.cargoService = cargoService;
    }

    @GetMapping(value = "/list")
    public String showAllCargoes(Model model) {

        if (logger.isDebugEnabled()){
            logger.debug("showAllCargoes is executed");
        }

        List<Cargo> cargoes = cargoService.listAll();
        model.addAttribute("cargoes", cargoes);
        return "manager/cargo/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {

        if (logger.isDebugEnabled()){
            logger.debug("show is executed");
        }

        model.addAttribute("cargo", cargoService.showCargo(id));
        model.addAttribute("statuses", CargoStatus.values());
        return "manager/cargo/show";
    }

    @GetMapping(value = "/create")
    public String createCargoForm(Model model) {

        if (logger.isDebugEnabled()){
            logger.debug("createCargoForm is executed");
        }

        model.addAttribute("cargo", new Cargo());
        return "manager/cargo/create";
    }

    @PostMapping(value = "/save")
    public String saveCargo(@ModelAttribute("cargo") @Valid Cargo cargo,
                            BindingResult bindingResult) {

        if (logger.isDebugEnabled()){
            logger.debug("saveCargo is executed");
        }

        if (bindingResult.hasErrors()) {
            return "manager/cargo/create";
        }
        cargoService.saveCargo(cargo);
        return "redirect:/cargoes/list";
    }

    @GetMapping(value = "/edit")
    public String editCargoForm(@RequestParam("cargoId") int id, Model model) {

        if (logger.isDebugEnabled()){
            logger.debug("editCargoForm is executed");
        }

        Cargo cargo = cargoService.getCargoById(id);
        model.addAttribute("cargo", cargo);
        model.addAttribute("statuses", CargoStatus.values());
        return "manager/cargo/edit";
    }

    @PostMapping("/update")
    public String updateCargo(@ModelAttribute("cargo") @Valid Cargo cargo,
                              BindingResult bindingResult) {

        if (logger.isDebugEnabled()){
            logger.debug("updateCargo is executed");
        }

        if (bindingResult.hasErrors()) {
            return "manager/cargo/edit";
        }
        cargoService.update(cargo);
        return "redirect:/cargoes/list";
    }

    @GetMapping(value = "/delete")
    public String deleteCargo(@RequestParam("cargoId") int id) {

        if (logger.isDebugEnabled()){
            logger.debug("deleteCargo is executed");
        }

        cargoService.delete(id);
        return "redirect:/cargoes/list";
    }
}
