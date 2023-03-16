package dev.osmocode.codehub.controller;

import dev.osmocode.codehub.dto.RegistrationUserDto;
import dev.osmocode.codehub.entity.Authority;
import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.service.AuthorityService;
import dev.osmocode.codehub.service.UserService;
import dev.osmocode.codehub.utils.Role;
import dev.osmocode.codehub.utils.UserCreationResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final AuthorityService authorityService;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UserService userService, AuthorityService authorityService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getRegister(Model model) {
        model.addAttribute("registrationDto", new RegistrationUserDto());
        return "register";
    }

    @PostMapping
    public ModelAndView postRegister(
            @Validated @ModelAttribute("registrationDto") RegistrationUserDto registrationDto,
            BindingResult result
    ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        modelAndView.addObject("registrationDto", registrationDto);
        if (result.hasErrors()) {
            return modelAndView;
        }
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmation())) {
            modelAndView.addObject("confirmationError", "true");
            return modelAndView;
        }
        Authority roleUser = authorityService.findAuthorityByName(Role.USER.toString());
        User user = new User(registrationDto.getUsername(), passwordEncoder.encode(registrationDto.getPassword()), registrationDto.getEmail(), roleUser);
        var userCreationResult = userService.saveUser(user);
        if (userCreationResult.isFailed() && userCreationResult.getField().equals(UserCreationResult.Field.USERNAME)) {
            modelAndView.addObject("usernameExists", "true");
            return modelAndView;
        }
        if (userCreationResult.isFailed() && userCreationResult.getField().equals(UserCreationResult.Field.EMAIL)) {
            modelAndView.addObject("emailExists", "true");
            return modelAndView;
        }
        modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }
}
