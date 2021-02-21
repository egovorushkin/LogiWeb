package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.entities.Truck;
import com.egovorushkin.logiweb.entities.enums.DriverStatus;
import com.egovorushkin.logiweb.entities.enums.TruckState;
import com.egovorushkin.logiweb.entities.enums.TruckStatus;
import com.egovorushkin.logiweb.services.api.CityService;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.TruckService;
import org.apache.log4j.Logger;
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

    private static final Logger LOGGER =
            Logger.getLogger(TruckController.class.getName());

    private static final String TRUCK_DTO = "truckDto";
    private static final String STATES = "states";
    private static final String CITIES = "cities";
    private static final String STATUSES = "statuses";
    private static final String REDIRECT_TRUCKS_LIST = "redirect:/trucks/list";
    private static final String MANAGER_TRUCK_CREATE = "manager/truck/create";

    private final TruckService truckService;
    private final CityService cityService;
    private final DriverService driverService;

    @Autowired
    public TruckController(TruckService truckService,
                           CityService cityService,
                           DriverService driverService) {
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
    public String showTruck(@PathVariable("id") Long id, Model model){
        model.addAttribute(TRUCK_DTO, truckService.getTruckById(id));
        model.addAttribute(STATES, TruckState.values());
        model.addAttribute(STATUSES, TruckStatus.values());
        model.addAttribute("currentDrivers", truckService.findCurrentDriversByTruckId(id));
        model.addAttribute("numberOfDrivers", truckService.findCurrentDriversByTruckId(id).size());

        return "manager/truck/show";
    }

    @GetMapping("/create")
    public String showCreateTruckForm(Model model) {
        model.addAttribute(TRUCK_DTO, new TruckDto());
        model.addAttribute(TRUCK_DTO, new Truck());
        model.addAttribute(CITIES, cityService.getAllCities());
        model.addAttribute(STATES, TruckState.values());
        return MANAGER_TRUCK_CREATE;
    }

    @PostMapping("/save")
    public String createTruck(@ModelAttribute(TRUCK_DTO) @Valid TruckDto truckDto,
                            BindingResult bindingResult, Model model) {

        String registrationNumber = truckDto.getRegistrationNumber();

        if (bindingResult.hasErrors()) {
            model.addAttribute(STATUSES, TruckStatus.values());
            model.addAttribute(CITIES, cityService.getAllCities());
            return MANAGER_TRUCK_CREATE;
        }

        Truck existingTruck = truckService.findByRegistrationNumber(registrationNumber);
        if (existingTruck != null) {
            model.addAttribute(TRUCK_DTO, new TruckDto());
            model.addAttribute(CITIES, cityService.getAllCities());
            model.addAttribute("createTruckError", "Truck registration number " +
                    "already exists");

            LOGGER.warn("Registration number already exists.");

            return MANAGER_TRUCK_CREATE;

        }
        truckService.createTruck(truckDto);
        return REDIRECT_TRUCKS_LIST;
    }


    @GetMapping("/edit")
    public String showEditTruckForm(@RequestParam("truckId") Long id, Model model) {
        model.addAttribute(TRUCK_DTO, truckService.getTruckById(id));
        model.addAttribute(CITIES, cityService.getAllCities());
        model.addAttribute(STATES, TruckState.values());
        model.addAttribute(STATUSES, TruckStatus.values());
        model.addAttribute("currentDrivers", truckService.findCurrentDriversByTruckId(id));
        model.addAttribute("availableDrivers",
                truckService.findAvailableDriversByTruck(truckService.getTruckById(id)));
        return "manager/truck/edit";
    }

    @PostMapping("/update")
    public String updateTruck(@ModelAttribute(TRUCK_DTO) @Valid TruckDto truckDto,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "manager/truck/edit";
        }
        truckService.updateTruck(truckDto);
        return REDIRECT_TRUCKS_LIST;
    }

    @GetMapping("/delete")
    public String deleteTruck(@RequestParam("truckId") Long id) {
        truckService.deleteTruck(id);
        return REDIRECT_TRUCKS_LIST;
    }

    @GetMapping("/bind-driver")
    public String bindDriverForTruck(@RequestParam("truckId") Long truckId,
                                    @RequestParam("driverId") Long driverId,
                                    Model model,
                                    RedirectAttributes redirectAttributes){
        TruckDto truck = truckService.getTruckById(truckId);

        DriverDto driver = driverService.getDriverById(driverId);

        if (truckService.findCurrentDriversByTruckId(truckId).size() >= truck.getTeamSize()) {
            model.addAttribute("noSpaceInTheTruck", "No space in the truck");
            model.addAttribute(TRUCK_DTO, truckService.getTruckById(truckId));
            model.addAttribute(CITIES, cityService.getAllCities());
            model.addAttribute(STATES, TruckState.values());
            model.addAttribute(STATUSES, TruckStatus.values());
            model.addAttribute("currentDrivers", truckService.findCurrentDriversByTruckId(truckId));
            model.addAttribute("availableDrivers",
                    truckService.findAvailableDriversByTruck(truckService.getTruckById(truckId)));
            return "manager/truck/edit";
        }

        driver.setTruck(truck);
        driverService.updateDriver(driver);

        truckService.updateTruck(truck);

        redirectAttributes.addAttribute("truckId", truckId);
        return "redirect:{truckId}";
    }

    @GetMapping("/unbind-driver")
    public String unbindDriverForTruck(@RequestParam("truckId") Long truckId,
                                    @RequestParam("driverId") Long driverId,
                                    RedirectAttributes redirectAttributes){
        DriverDto driverDto = driverService.getDriverById(driverId);

        if (driverDto.getTruck().getCurrentDrivers() != null) {
            DriverDto colleague = driverDto.getTruck().getCurrentDrivers().stream()
                    .filter(driver -> driver.getId() != driverDto.getId())
                    .findFirst().orElse(null);

            if (colleague != null) {
                colleague.setTruck(null);
                colleague.setInShift(false);
                colleague.setStatus(DriverStatus.RESTING);
                driverService.updateDriver(colleague);
            }
        }

        if (truckService.findCurrentDriversByTruckId(driverDto.getTruck().getId()).size() == 1) {
            driverDto.getTruck().setStatus(TruckStatus.PARKED);
            driverDto.getTruck().setBusy(false);
            truckService.updateTruck(driverDto.getTruck());
        }

        driverDto.setTruck(null);
        driverDto.setInShift(false);
        driverDto.setStatus(DriverStatus.RESTING);

        driverService.updateDriver(driverDto);

        redirectAttributes.addAttribute("truckId", truckId);
        return "redirect:{truckId}";
    }

}
