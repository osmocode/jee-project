package dev.osmocode.codehub.controller;


import dev.osmocode.codehub.UserDto;
import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Optional;

@Controller
public class ProfileController {
    
    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile/{username}")
    public ModelAndView getProfile(@PathVariable String username) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<User> optionalUser = userService.findUserByUserName(username);
        if (optionalUser.isEmpty()) {
            modelAndView.setViewName("error");
            modelAndView.addObject("timestamp", new Date().toString());
            modelAndView.addObject("path", "/profile/" +  username);
            modelAndView.addObject("error", "Not found");
            modelAndView.addObject("status", HttpStatus.NOT_FOUND);
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
            return modelAndView;
        }
        
        modelAndView.setViewName("profile");
        modelAndView.addObject("userDto", UserDto.buildFrom(optionalUser.get()));
        return modelAndView;
    }
}
