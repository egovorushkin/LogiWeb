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

    private static final String CARGO = "cargo";
    private static final String REDIRECT_CARGOES_LIST =  "redirect:/cargoes/list";

    private final CargoService cargoService;

    @Autowired
    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping("/list/{pageId}")
    public String showCargoesByPage(@PathVariable("pageId") int pageId, Model model) {
        int recordsByPage = 6;
        Long totalPages = (cargoService.totalCount() / recordsByPage);

        if (pageId != 1) {
            pageId = (pageId - 1) * recordsByPage + 1;
        }

        model.addAttribute("cargoes",
                cargoService.listAllByPage(pageId, recordsByPage));
        model.addAttribute("totalPages", totalPages);
        return "manager/cargo/list";
    }

    @GetMapping("/{id}")
    public String showCargo(@PathVariable("id") Long id, Model model) {
        model.addAttribute(CARGO, cargoService.getCargoById(id));
        model.addAttribute("statuses", CargoStatus.values());
        return "manager/cargo/show";
    }

    @GetMapping("/create")
    public String showCreateCargoForm(Model model) {
        model.addAttribute(CARGO, new CargoDto());
        return "manager/cargo/create";
    }

    @PostMapping("/save")
    public String createCargo(@ModelAttribute(CARGO) @Valid CargoDto cargoDto,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "manager/cargo/create";
        }
        cargoService.createCargo(cargoDto);
        return REDIRECT_CARGOES_LIST;
    }

    @GetMapping("/edit")
    public String showEditCargoForm(@RequestParam("cargoId") Long id, Model model) {
        model.addAttribute(CARGO, cargoService.getCargoById(id));
        model.addAttribute("statuses", CargoStatus.values());
        return "manager/cargo/edit";
    }

    @PostMapping("/update")
    public String updateCargo(@ModelAttribute(CARGO) @Valid CargoDto cargoDto,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "manager/cargo/edit";
        }
        cargoService.updateCargo(cargoDto);
        return REDIRECT_CARGOES_LIST;
    }

    @GetMapping("/delete")
    public String deleteCargo(@RequestParam("cargoId") Long id) {
        cargoService.deleteCargo(id);
        return REDIRECT_CARGOES_LIST;
    }
}
