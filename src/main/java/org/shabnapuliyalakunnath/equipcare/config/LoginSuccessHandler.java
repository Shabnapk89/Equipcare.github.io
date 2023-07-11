package org.shabnapuliyalakunnath.equipcare.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        String redirectURL = request.getContextPath();

        Set<String> roles = authentication.getAuthorities().stream()
                .map(r -> r.getAuthority()).collect(Collectors.toSet());

        if(roles.contains("Admin")) {
            redirectURL = "/hems/home";
        }
        else if(roles.contains("MaintenanceEngineer")){
            redirectURL = "/hems/engHome";
        }
        else if (roles.contains("StaffIncharge")) {
            redirectURL = "/hems/staffHome";
        }
        response.sendRedirect(redirectURL);

    }
}
