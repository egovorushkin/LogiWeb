package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.UserDto;
import com.egovorushkin.logiweb.entities.User;
import com.egovorushkin.logiweb.services.api.CityService;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.ScoreboardService;
import com.egovorushkin.logiweb.services.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private static final String USER_DTO = "userDto";
    private static final String REG_FORM = "registration/" +
            "registration-form";
    private static final String REG_FORM_ADMIN =
            "registration/registration-form-admin";
    private static final String CITIES = "cities";
    private static final String REG_ERROR = "registrationError";
    private static final String USERNAME_EXISTS = "User name already exists: ";

    private final UserService userService;
    private final DriverService driverService;
    private final CityService cityService;
    private final ScoreboardService scoreboardService;

    private static final Logger LOGGER =
            Logger.getLogger(RegistrationController.class.getName());

    @Autowired
    public RegistrationController(UserService userService,
                                  DriverService driverService,
                                  CityService cityService,
                                  ScoreboardService scoreboardService) {
        this.userService = userService;
        this.driverService = driverService;
        this.cityService = cityService;
        this.scoreboardService = scoreboardService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showRegistrationForm(Model model) {
        model.addAttribute(USER_DTO, new UserDto());
        model.addAttribute(CITIES, cityService.getAllCities());
        return REG_FORM;
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute(USER_DTO)
                                                      UserDto userDto,
                                          BindingResult theBindingResult,
                                          Model model) {

        String userName = userDto.getUserName();

        if (theBindingResult.hasErrors()) {
            model.addAttribute(USER_DTO, new UserDto());
            model.addAttribute(REG_ERROR, "User name/password " +
                    "can not be empty.");
            model.addAttribute(CITIES, cityService.getAllCities());

            LOGGER.warn("User name/password can not be empty.");

            return REG_FORM;
        }

        User existing = userService.findByUserName(userName);
        if (existing != null) {
            model.addAttribute(USER_DTO, new UserDto());
            model.addAttribute(CITIES, cityService.getAllCities());
            model.addAttribute(REG_ERROR, USERNAME_EXISTS + userName);

            LOGGER.warn(USERNAME_EXISTS + userName);
            return REG_FORM;
        }

        DriverDto driverDto = new DriverDto();
        driverDto.setUsername(userDto.getUserName());
        driverDto.setFirstName(userDto.getFirstName());
        driverDto.setLastName(userDto.getLastName());
        driverDto.setCurrentCity(userDto.getCurrentCity());

        driverService.createDriver(driverDto);
        userService.save(userDto);

        scoreboardService.updateScoreboard();

        LOGGER.info("Successfully created user with username: " + userName);

        return "registration/registration-confirmation";
    }

    @GetMapping("/admin/showRegistrationFormForAdmin")
    public String showRegistrationFormForAdmin(Model model) {
        model.addAttribute(USER_DTO, new UserDto());
        return REG_FORM_ADMIN;
    }

    @PostMapping("/admin/processRegistrationFormForAdmin")
    public String processRegistrationFormForAdmin(@Valid @ModelAttribute(USER_DTO) UserDto userDto,
                                          BindingResult theBindingResult,
                                          Model model) {

        String userName = userDto.getUserName();

        if (theBindingResult.hasErrors()) {
            model.addAttribute(USER_DTO, new UserDto());
            model.addAttribute(REG_ERROR, "User name/password " +
                    "can not be empty.");

            LOGGER.warn("User name/password can not be empty.");

            return REG_FORM_ADMIN;
        }

        User existing = userService.findByUserName(userName);
        if (existing != null) {
            model.addAttribute(USER_DTO, new UserDto());
            model.addAttribute(REG_ERROR, USERNAME_EXISTS + userName);

            LOGGER.warn(USERNAME_EXISTS + userName);
            return REG_FORM_ADMIN;
        }

        userService.saveAdmin(userDto);

        LOGGER.info("Successfully created user with username: " + userName);

        return "registration/registration-confirmation-admin";
    }

}
