

// Controller class to create,edit ,delete and list equipments

package org.shabnapuliyalakunnath.equipcare.controller;

import org.shabnapuliyalakunnath.equipcare.dto.EquipmentDto;
import org.shabnapuliyalakunnath.equipcare.dto.UserDto;
import org.shabnapuliyalakunnath.equipcare.service.EquipmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("hems")

public class EquipmentController {


    @Autowired
    EquipmentService equipmentService;

    //API 1: Create User : POST
    @PostMapping("/createEquipment")//post method to create equipment
    public String createEquipment(@Valid @ModelAttribute("equipment") EquipmentDto equipment , BindingResult result, Model model,
                                  RedirectAttributes redirectAttrs) {
        List<UserDto> staffIncharges = equipmentService.getStaffInchargeUsers();
        model.addAttribute("staffIncharges", staffIncharges);
        if(result.hasErrors()) {
            return "createEquipment";
        }
       String res= equipmentService.createEquipment(equipment);
        if(!"Success".equalsIgnoreCase(res)) {
            model.addAttribute("errorMsg",res);
            model.addAttribute("isSuccess",false);
            return "createEquipment";
        }
       // return "createEquipment";

        redirectAttrs.addFlashAttribute("message","equipment Created Successfully!!");
        return "redirect:/hems/listEquipments";
    }
   // @PreAuthorize("hasRole('Admin')")
    @GetMapping("/createEquipment")   //http://localhost:8080/hems/    - GET
    public String createEquipment(Model model) {
        model.addAttribute("equipment", new EquipmentDto());
        List<UserDto> staffIncharges = equipmentService.getStaffInchargeUsers();
        model.addAttribute("staffIncharges", staffIncharges);
        return "createEquipment";
    }

    @GetMapping("/listEquipments")   //http://localhost:8080/hems/    - GET
    public String list(Model model) {
        List <EquipmentDto> equipmentDtos  = equipmentService.getEquipments();
        model.addAttribute("equipmentList", equipmentDtos);
        return "viewEquipment";
    }

    @GetMapping("/deleteEquipment")
    public String deleteEquipment(@RequestParam Long equipmentId, Model model, RedirectAttributes redirectAttrs) {
        String res = equipmentService.deleteEquipment(String.valueOf(equipmentId));
        if(!"Success".equalsIgnoreCase(res)) {
            redirectAttrs.addFlashAttribute("errorMsg",res);
        }else {
            redirectAttrs.addFlashAttribute("message","Equipment Deleted Successfully!!");
        }

        return "redirect:/hems/listEquipments";
    }

    @GetMapping("/editEquipment")   //http://localhost:8080/hems/    - GET
    public String editEquipment(@RequestParam String equipmentId, Model model) {
        EquipmentDto equipment = equipmentService.getEquipment(equipmentId);
        model.addAttribute("equipment", equipment);
        List<UserDto> staffIncharges = equipmentService.getStaffInchargeUsers();
        model.addAttribute("staffIncharges", staffIncharges);
        return "editEquipment";
    }

    @PostMapping("/editEquipment")
    public String editEquipment(@Valid @ModelAttribute("equipment") EquipmentDto equipment, BindingResult result, Model model, RedirectAttributes redirectAttrs) {
        List<UserDto> staffIncharges = equipmentService.getStaffInchargeUsers();
        model.addAttribute("staffIncharges", staffIncharges);
        if(result.hasErrors()) {
            return "editEquipment";
        }
        String res = equipmentService.edituser(equipment);
        if(!"Success".equalsIgnoreCase(res)) {
            model.addAttribute("errorMsg",res);
            return "editEquipment";
        }
        redirectAttrs.addFlashAttribute("message","Equipment Updated Successfully!!");
        return "redirect:/hems/listEquipments";
    }
}