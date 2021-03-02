package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.entities.Truck;
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
    private static final String MANAGER_TRUCK_EDIT = "manager/truck/edit";
    private static final String CURRENT_DRIVERS = "currentDrivers";

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
    public String showTruck(@PathVariable("id") Long id, Model model) {
        TruckDto truckDto = truckService.getTruckById(id);

        model.addAttribute(TRUCK_DTO, truckDto);
        model.addAttribute(STATES, TruckState.values());
        model.addAttribute(STATUSES, TruckStatus.values());
        model.addAttribute(CURRENT_DRIVERS, truckDto.getDrivers());
        model.addAttribute("numberOfDrivers",
                truckDto.getDrivers().size());

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

        Truck existingTruck =
                truckService.findByRegistrationNumber(registrationNumber);

        if (existingTruck != null) {
            model.addAttribute(TRUCK_DTO, new TruckDto());
            model.addAttribute(CITIES, cityService.getAllCities());
            model.addAttribute("createTruckError",
                    "Truck registration number already exists");

            LOGGER.warn("Registration number already exists.");

            return MANAGER_TRUCK_CREATE;
        }
        truckService.createTruck(truckDto);
        return REDIRECT_TRUCKS_LIST;
    }

    @GetMapping("/edit")
    public String showEditTruckForm(@RequestParam("truckId") Long id,
                                    Model model) {
        TruckDto truckDto = truckService.getTruckById(id);
        model.addAttribute(TRUCK_DTO, truckDto);
        model.addAttribute(CITIES, cityService.getAllCities());
        model.addAttribute(STATES, TruckState.values());
        model.addAttribute(STATUSES, TruckStatus.values());
        model.addAttribute(CURRENT_DRIVERS, truckDto);
        model.addAttribute("availableDrivers",
                truckService.findAvailableDriversByTruck(truckDto));
        return MANAGER_TRUCK_EDIT;
    }

    @PostMapping("/update")
    public String updateTruck(@ModelAttribute(TRUCK_DTO) @Valid TruckDto truckDto,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MANAGER_TRUCK_EDIT;
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
                                     RedirectAttributes redirectAttributes) {
        TruckDto truckDto = truckService.getTruckById(truckId);

        DriverDto driverDto = driverService.getDriverById(driverId);

        if (truckDto.getDrivers().size() >= truckDto.getTeamSize()) {
            model.addAttribute("noSpaceInTheTruck",
                    "No space in the truck");
            model.addAttribute(TRUCK_DTO, truckDto);
            model.addAttribute(CITIES, cityService.getAllCities());
            model.addAttribute(STATES, TruckState.values());
            model.addAttribute(STATUSES, TruckStatus.values());
            model.addAttribute(CURRENT_DRIVERS, truckDto.getDrivers());
            model.addAttribute("availableDrivers",
                    truckService.findAvailableDriversByTruck(truckDto));
            return MANAGER_TRUCK_EDIT;
        }

        driverDto.setTruck(truckDto);
        driverService.updateDriver(driverDto);

        truckService.updateTruck(truckDto);

        redirectAttributes.addAttribute("truckId", truckId);
        return "redirect:{truckId}";
    }

    @GetMapping("/unbind-driver")
    public String unbindDriverForTruck(@RequestParam("truckId") Long truckId,
                                       @RequestParam("driverId") Long driverId,
                                       RedirectAttributes redirectAttributes) {

        truckService.unbindDriver(truckId, driverId);

        redirectAttributes.addAttribute("truckId", truckId);
        return "redirect:{truckId}";
    }

}
