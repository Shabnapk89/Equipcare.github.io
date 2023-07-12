package org.shabnapuliyalakunnath.equipcare.controller;

import org.shabnapuliyalakunnath.equipcare.dto.UserDto;
import org.shabnapuliyalakunnath.equipcare.exceptions.UserIdMismatchException;
import org.shabnapuliyalakunnath.equipcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("hems")
public class UserController {

   @Autowired
   private UserService userService;


    //API 1: Create User : POST
    @PostMapping("/createUser")
    public String createUser(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model,
                             RedirectAttributes redirectAttrs) {
        if(result.hasErrors()) {
            return "createUser";
        }
        String res = "";
        try{
            res = userService.createUser(user);
        }catch (UserIdMismatchException e) {
            res = e.getMessage();   // Ex
        }

        if(!"Success".equalsIgnoreCase(res)) {
            model.addAttribute("errorMsg",res);
            model.addAttribute("isSuccess",false);
            return "createUser";
        }
        redirectAttrs.addFlashAttribute("message","User Created Successfully!!");
        return "redirect:/hems/listUser";
    }

    @GetMapping("/createuser")   //http://localhost:8080/hems/    - GET
    public String createuser(Model model) {
        model.addAttribute("user", new UserDto());
        return "createUser";
    }

    @GetMapping("/listUser")   //http://localhost:8080/hems/    - GET
    public String list(Model model) {
        List <UserDto> users = userService.getUsers();
        model.addAttribute("userList", users);
        return "viewUsers";
    }
    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam String userId, Model model, RedirectAttributes redirectAttrs) {
        String res = userService.deleteUser(userId);
        if(!"Success".equalsIgnoreCase(res)) {
            redirectAttrs.addFlashAttribute("errorMsg",res);
        }else {
            redirectAttrs.addFlashAttribute("message","User Deleted Successfully!!");
        }

        return "redirect:/hems/listUser";
    }

    @GetMapping("/editUser")   //http://localhost:8080/hems/    - GET
    public String list(@RequestParam String userId, Model model) {
        UserDto user = userService.getUser(userId);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/editUser")
    public String editUser(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model, RedirectAttributes redirectAttrs) {
        model.addAttribute("isSuccess",true);
        if(result.hasErrors()) {
            return "editUser";
        }
        String res = userService.edituser(user);
        if(!"Success".equalsIgnoreCase(res)) {
            model.addAttribute("errorMsg",res);
            model.addAttribute("isSuccess",false);
            return "editUser";
        }
        redirectAttrs.addFlashAttribute("message","User Updated Successfully!!");
        return "redirect:/hems/listUser";
    }
}
