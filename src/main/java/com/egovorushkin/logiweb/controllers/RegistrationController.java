package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.User;
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

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private Map<String, String> roles;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = Logger.getLogger(RegistrationController.class.getName());

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostConstruct
    protected void loadRoles(){
        roles = new LinkedHashMap<>();

        roles.put("ROLE_DRIVER", "Driver");
        roles.put("ROLE_ADMIN", "Admin");
    }

    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model model){
        model.addAttribute("driver", new Driver());
        model.addAttribute("roles", roles);

        return "registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("driver") Driver driver,
                                          BindingResult bindingResult, Model model) {
        String userName = driver.getUsername();

        LOGGER.info("Processing registration form for: " + userName);

        // form validation
        if (bindingResult.hasErrors()) {
            model.addAttribute("driver", new Driver());
            model.addAttribute("roles", roles);
            model.addAttribute("registrationError", "User name/password can not be empty.");

            LOGGER.warn("User name/password can not be empty.");

            return "registration-form";
        }

        // check the database if user already exists
        User existingUser = userService.findByUserName(userName);

        if (existingUser != null) {
            model.addAttribute("driver", new Driver());
            model.addAttribute("registrationError", "Username already exists.");

            LOGGER.warn("Username already exists.");

            return "registration-form";
        }

        // encrypt the password
        String encodedPassword = passwordEncoder.encode(driver.getPassword());

        // prepend the encoding algorithm id
        encodedPassword = "{bcrypt}" + encodedPassword;

        // give user default role of "driver"
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList();
        authorities.add(new SimpleGrantedAuthority("ROLE_DRIVER"));

        userService.save(driver);

        LOGGER.info("Successfully created user: " + userName);

        return "registration-confirmation";
    }
}
