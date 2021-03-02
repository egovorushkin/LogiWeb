package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.dto.UserDto;
import com.egovorushkin.logiweb.entities.User;
import com.egovorushkin.logiweb.entities.enums.DriverStatus;
import com.egovorushkin.logiweb.services.api.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    private static final Logger LOGGER =
            Logger.getLogger(DriverController.class.getName());

    private static final String CITIES = "cities";
    private static final String USER_DTO = "userDto";
    private static final String REDIRECT_DRIVERS_LIST = "redirect:/drivers/list";
    private static final String MANAGER_DRIVER_CREATE = "manager/driver/create";

    private final DriverService driverService;
    private final TruckService truckService;
    private final CityService cityService;
    private final UserService userService;
    private final ScoreboardService scoreboardService;

    @Autowired
    public DriverController(DriverService driverService,
                            TruckService truckService,
                            CityService cityService,
                            UserService userService,
                            ScoreboardService scoreboardService) {
        this.driverService = driverService;
        this.truckService = truckService;
        this.cityService = cityService;
        this.userService = userService;
        this.scoreboardService = scoreboardService;
    }

    @GetMapping("/list")
    public String showAllDrivers(Model model) {
        model.addAttribute("drivers", driverService.getAllDrivers());
        return "manager/driver/list";
    }

    @GetMapping("/{id}")
    public String showDriver(@PathVariable("id") Long id, Model model) {
        model.addAttribute("driver", driverService.getDriverById(id));
        model.addAttribute(CITIES, cityService.getAllCities());
        model.addAttribute("trucks", truckService.getAllTrucks());
        return "manager/driver/show";
    }

    @GetMapping("/create")
    public String showCreateDriverForm(Model model) {
        model.addAttribute(USER_DTO, new UserDto());
        model.addAttribute(CITIES, cityService.getAllCities());
        return MANAGER_DRIVER_CREATE;
    }

    @PostMapping("/save")
    public String saveDriver(@Valid @ModelAttribute(USER_DTO) UserDto userDto,
                             BindingResult theBindingResult,
                             Model model) {
        String userName = userDto.getUserName();

        if (theBindingResult.hasErrors()) {
            model.addAttribute(USER_DTO, new UserDto());
            model.addAttribute("registrationError", "User name/password " +
                    "can not be empty.");
            model.addAttribute(CITIES, cityService.getAllCities());

            LOGGER.warn("User name/password can not be empty.");

            return MANAGER_DRIVER_CREATE;
        }

        User existingUser = userService.findByUserName(userName);
        if (existingUser != null) {
            model.addAttribute(USER_DTO, new UserDto());
            model.addAttribute(CITIES, cityService.getAllCities());
            model.addAttribute("registrationError",
                    "User name already exists: " + userName);

            LOGGER.warn("User name already exists: " + userName);
            return MANAGER_DRIVER_CREATE;
        }

        DriverDto driverDto = new DriverDto();
        driverDto.setUsername(userDto.getUserName());
        driverDto.setFirstName(userDto.getFirstName());
        driverDto.setLastName(userDto.getLastName());

        driverDto.setCurrentCity(userDto.getCurrentCity());
        driverService.createDriver(driverDto);

        scoreboardService.updateScoreboard();

        userService.save(userDto);

        LOGGER.info("Successfully created driver: " + userName);

        return REDIRECT_DRIVERS_LIST;
    }

    @GetMapping("/edit")
    public String showEditDriverForm(@RequestParam("driverId") Long id,
                                     Model model) {
        model.addAttribute("driver", driverService.getDriverById(id));
        model.addAttribute("statuses", DriverStatus.values());
        model.addAttribute("availableTrucks",
                driverService.findAvailableTrucksByDriver(driverService.getDriverById(id)));
        model.addAttribute(CITIES, cityService.getAllCities());
        return "manager/driver/edit";
    }

    @PostMapping("/update")
    public String updateDriver(@ModelAttribute("driver") @Valid DriverDto driverDto,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "manager/driver/edit";
        }
        driverService.updateDriver(driverDto);
        return REDIRECT_DRIVERS_LIST;
    }

    @GetMapping("/delete")
    public String deleteDriver(@RequestParam("driverId") Long id) {
        driverService.deleteDriver(id);
        return REDIRECT_DRIVERS_LIST;
    }

    @GetMapping("/bind-truck")
    public String bindTruckForDriver(@RequestParam("truckId") Long truckId,
                                    @RequestParam("driverId") Long driverId,
                                    RedirectAttributes redirectAttributes) {
        TruckDto truck = truckService.getTruckById(truckId);
        DriverDto driver = driverService.getDriverById(driverId);
        if (truck.getDrivers().size() < truck.getTeamSize()) {
            driver.setTruck(truck);
            driverService.updateDriver(driver);
        }

        redirectAttributes.addAttribute("driverId", driverId);
        return "redirect:{driverId}";
    }

    @GetMapping("/unbind-truck")
    public String unbindDriverForTruck(@RequestParam("driverId") Long driverId,
                                       RedirectAttributes redirectAttributes) {
        DriverDto driver = driverService.getDriverById(driverId);
        if (driver.getTruck() != null) {
            driver.setTruck(null);
            driverService.updateDriver(driver);
        }

        redirectAttributes.addAttribute("driverId", driverId);
        return "redirect:{driverId}";
    }

}
