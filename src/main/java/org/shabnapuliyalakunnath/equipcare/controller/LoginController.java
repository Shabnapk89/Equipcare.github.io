package org.shabnapuliyalakunnath.equipcare.controller;


import org.shabnapuliyalakunnath.equipcare.dto.MaintCountDto;
import org.shabnapuliyalakunnath.equipcare.dto.MaintenanceDto;
import org.shabnapuliyalakunnath.equipcare.dto.UserDto;
import org.shabnapuliyalakunnath.equipcare.service.MaintenanceService;
import org.shabnapuliyalakunnath.equipcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("hems")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    MaintenanceService maintenanceService;

    @GetMapping("/")
    public String login_1(Model model) {
        return "login";
    }
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/staffHome")
    public String staffHome(Model model) {
        return "staffHome";
    }

    @GetMapping("/engHome")
    public String engHome(Model model,Principal principal, Authentication authentication) {
        MaintCountDto maintCountDto = new MaintCountDto();
        List<MaintenanceDto> maintenanceDtos  = new ArrayList<>();
        Set<String> roles = authentication.getAuthorities().stream()
                .map(r -> r.getAuthority()).collect(Collectors.toSet());
        if(roles.contains("Admin")) {
            maintenanceDtos  = maintenanceService.getMaintenance(null);
        }else{
            maintenanceDtos  = maintenanceService.getMaintenance(principal.getName());
        }

        model.addAttribute("maintenances", maintenanceDtos);
        return "engHome";
    }

    @GetMapping("/register")
    public String register(Model model) {

        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register/check")
    public String registerCheck(@ModelAttribute("user") UserDto user, Model model) {

        Boolean eligible = userService.isEligibleForRegister(user.getEmail());
        model.addAttribute("isValid", eligible);
        return "register";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserDto user, Model model) {
        userService.register(user);
        model.addAttribute("isSuccess", true);
        model.addAttribute("user", new UserDto());
        return "register";
    }

}

