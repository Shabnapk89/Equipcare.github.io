package org.shabnapuliyalakunnath.equipcare.controller;

//import com.perscholas.hems.dto.*;
import org.shabnapuliyalakunnath.equipcare.dto.*;
import org.shabnapuliyalakunnath.equipcare.service.EquipmentService;
import org.shabnapuliyalakunnath.equipcare.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/hems")
public class MaintenanceController {
    @Autowired
    MaintenanceService maintenanceService;
    @Autowired
    EquipmentService equipmentService;

    @GetMapping("/maintainEquipment")   //http://localhost:8080/hems/    - GET
    public String maintainEquipment(@RequestParam(required = false) Long equipmentId, Model model) {
        MaintenanceDto maintain = new MaintenanceDto();
        if(null != equipmentId) {
            maintain.setEquipmentId(equipmentId);
        }
        List <EquipmentDto> equipmentDtos  = equipmentService.getEquipments();
        model.addAttribute("equipmentList", equipmentDtos);
        List<UserDto> engineerUsers = equipmentService.getEngineerUsers();
        model.addAttribute("engineerUsers", engineerUsers);
        model.addAttribute("maintain", maintain);
        return "maintainEquipment";
    }
    @PostMapping("/maintainEquipment")
    public String maintainEquipment(@Valid @ModelAttribute("maintain") MaintenanceDto maintain, BindingResult result, Model model, RedirectAttributes redirectAttrs) {
        if(result.hasErrors()) {
            return "maintainEquipment";
        }
        String res = maintenanceService.maintainEquipment(maintain);
        redirectAttrs.addFlashAttribute("message","Maintenance Created Successfully!!");
        return "redirect:/hems/ViewMaintenance";
    }
    @GetMapping("/ViewMaintenance")   //http://localhost:8080/hems/    - GET
    public String ViewMaintenance(Model model, Principal principal, Authentication authentication) {
        MaintCountDto maintCountDto = new MaintCountDto();
        List <MaintenanceDto> maintenanceDtos  = new ArrayList<>();
        Set<String> roles = authentication.getAuthorities().stream()
                .map(r -> r.getAuthority()).collect(Collectors.toSet());
        if(roles.contains("Admin")) {
            maintenanceDtos  = maintenanceService.getMaintenance(null);
        }else{
            maintenanceDtos  = maintenanceService.getMaintenance(principal.getName());
        }

        model.addAttribute("maintenances", maintenanceDtos);
        return "ViewMaintenance";
    }

    @GetMapping("/maintainHistory")   //http://localhost:8080/hems/    - GET
    public String maintainHistory(@RequestParam Long equipmentId, Model model) {
        History maintenanceHistory  = maintenanceService.getMaintenanceHistory(equipmentId);
        model.addAttribute("history", maintenanceHistory);
        return "maintainHistory";
    }

    @GetMapping("/changeStatus")
    public String changeStatus(@RequestParam(required = false, name = "maintId") String maintId,
                               @RequestParam(required = false, name = "status") String status,
                               Model model,Principal principal, Authentication authentication,
                               RedirectAttributes redirectAttrs) {

        maintenanceService.changeStatus(Long.parseLong(maintId), status);
        redirectAttrs.addFlashAttribute("message","Maintenance Status Changed to "+status);
        return "redirect:/hems/ViewMaintenance";
    }

}
