
// Rest Controller class to home page

package org.shabnapuliyalakunnath.equipcare.controller;

import org.shabnapuliyalakunnath.equipcare.dto.MaintCountDto;
import org.shabnapuliyalakunnath.equipcare.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hems")
public class HomeController {

    @Autowired
    MaintenanceService maintenanceService;

    @GetMapping("/getMaintCount")
    public MaintCountDto getMaintCount(Principal principal, Authentication authentication) {

        MaintCountDto maintCountDto = new MaintCountDto();
        Set<String> roles = authentication.getAuthorities().stream()
                .map(r -> r.getAuthority()).collect(Collectors.toSet());
        if(roles.contains("Admin")) {
            maintCountDto = maintenanceService.getMaintCount();
        }else{
            maintCountDto = maintenanceService.getMaintCount(principal.getName());
        }
        return maintCountDto;
     }

}
