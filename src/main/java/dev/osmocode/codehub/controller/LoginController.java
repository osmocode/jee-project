package dev.osmocode.codehub.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLogin(Authentication authentication) {
        return authentication == null ? "login" : "redirect:/dashboard";
    }

}
