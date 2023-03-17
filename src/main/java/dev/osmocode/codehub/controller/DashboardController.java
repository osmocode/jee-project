package dev.osmocode.codehub.controller;

import dev.osmocode.codehub.dto.UserProfileDto;
import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@Controller
public class DashboardController {
    
    private final UserService userService;

    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    //TODO: Add pagination for followings
    @GetMapping("/dashboard")
    public ModelAndView getTemplate(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dashboard");
        User user = userService.findUserByUserName(authentication.getName()).orElseThrow(() -> new RuntimeException(""));
        modelAndView.addObject("demo", "# Demo title\n## Sub title\n```py\ntoto=[]\ntoto.appen(10)\n```");
        return modelAndView;
    }

}
