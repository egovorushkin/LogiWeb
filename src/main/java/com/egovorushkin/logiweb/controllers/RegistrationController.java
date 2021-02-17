package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dao.api.RoleDao;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private static final String USER_DTO = "userDto";
    private static final String REGISTRATION_FORM = "registration-form";
    private static final String CITIES = "cities";

    private final UserService userService;
    private final DriverService driverService;
    private final CityService cityService;
    private final RoleDao roleDao;
    private final ScoreboardService scoreboardService;

    private static final Logger LOGGER =
            Logger.getLogger(RegistrationController.class.getName());

    @Autowired
    public RegistrationController(UserService userService,
                                  DriverService driverService,
                                  CityService cityService, RoleDao roleDao,
                                  ScoreboardService scoreboardService) {
        this.userService = userService;
        this.driverService = driverService;
        this.cityService = cityService;
        this.roleDao = roleDao;
        this.scoreboardService = scoreboardService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model model) {
        model.addAttribute(USER_DTO, new UserDto());
        model.addAttribute(CITIES, cityService.getAllCities());
        model.addAttribute("roles", roleDao.getAllRoles());

        return REGISTRATION_FORM;
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute(USER_DTO) UserDto userDto,
                                          BindingResult theBindingResult,
                                          Model model) {

        String userName = userDto.getUserName();

        if (theBindingResult.hasErrors()) {
            model.addAttribute(USER_DTO, new UserDto());
            model.addAttribute("roles", roleDao.getAllRoles());
            model.addAttribute("registrationError", "User name/password " +
                    "can not be empty.");
            model.addAttribute(CITIES, cityService.getAllCities());

            LOGGER.warn("User name/password can not be empty.");

            return REGISTRATION_FORM;
        }

        User existing = userService.findByUserName(userName);
        if (existing != null) {
            model.addAttribute(USER_DTO, new UserDto());
            model.addAttribute(CITIES, cityService.getAllCities());
            model.addAttribute("registrationError", "User name already " +
                    "exists.");

            LOGGER.warn("User name already exists.");
            return REGISTRATION_FORM;
        }

        List<GrantedAuthority> authorities =
                AuthorityUtils.createAuthorityList();
        authorities.add(new SimpleGrantedAuthority("ROLE_DRIVER"));

        String formRole = userDto.getFormRole();

        if (!formRole.equals("ROLE_DRIVER")) {
            authorities.add(new SimpleGrantedAuthority(formRole));
        }

        DriverDto driverDto = new DriverDto();
        driverDto.setUsername(userDto.getUserName());
        driverDto.setFirstName(userDto.getFirstName());
        driverDto.setLastName(userDto.getLastName());

        driverDto.setCurrentCity(userDto.getCurrentCity());
        driverService.createDriver(driverDto);

        scoreboardService.updateScoreboard();

        userService.save(userDto);

        LOGGER.info("Successfully created user: " + userName);

        return "registration-confirmation";
    }

}
