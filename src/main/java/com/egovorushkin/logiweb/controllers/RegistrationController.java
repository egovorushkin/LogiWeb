package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dao.api.RoleDao;
import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.UserDto;
import com.egovorushkin.logiweb.entities.User;
import com.egovorushkin.logiweb.services.api.CityService;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private Map<String, String> roles;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserService userService;
    private final DriverService driverService;
    private final CityService cityService;
    private final RoleDao roleDao;

    private final Logger LOGGER = Logger.getLogger(RegistrationController.class.getName());

    @Autowired
    public RegistrationController(UserService userService,
                                  DriverService driverService, CityService cityService, RoleDao roleDao) {
        this.userService = userService;
        this.driverService = driverService;
        this.cityService = cityService;
        this.roleDao = roleDao;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model theModel) {
        theModel.addAttribute("userDto", new UserDto());
        theModel.addAttribute("cities", cityService.getAllCities());
        theModel.addAttribute("roles", roleDao.getAllRoles());

        return "registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("userDto") UserDto userDto,
                                          BindingResult theBindingResult, Model theModel) {

        String userName = userDto.getUserName();

        if (theBindingResult.hasErrors()) {
            theModel.addAttribute("userDto", new UserDto());
            theModel.addAttribute("roles", roles);
            theModel.addAttribute("registrationError", "User name/password can not be empty.");
            theModel.addAttribute("cities", cityService.getAllCities());

            LOGGER.warn("User name/password can not be empty.");

            return "registration-form";
        }

        User existing = userService.findByUserName(userName);
        if (existing != null) {
            theModel.addAttribute("userDto", new UserDto());
            theModel.addAttribute("registrationError", "User name already exists.");

            LOGGER.warn("User name already exists.");
            return "registration-form";
        }

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        encodedPassword = "{bcrypt}" + encodedPassword;

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList();
        authorities.add(new SimpleGrantedAuthority("ROLE_DRIVER"));

        String formRole = userDto.getFormRole();

        if (!formRole.equals("ROLE_DRIVER")) {
            authorities.add(new SimpleGrantedAuthority(formRole));
        }

        DriverDto driverDto = new DriverDto();
        driverDto.setUsername(userDto.getUserName());
        driverDto.setFirstName(userDto.getFirstName());
        driverDto.setLastName(userDto.getLastName());
        // TODO refactor this
        driverDto.setPersonalNumber(new Random().nextInt(1000 - 1) + 1);
        driverDto.setCurrentCity(userDto.getCurrentCity());
        driverService.createDriver(driverDto);

        userService.save(userDto);

        LOGGER.info("Successfully created user: " + userName);

        return "registration-confirmation";
    }

}
