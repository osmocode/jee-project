package dev.osmocode.codehub.controller;

import dev.osmocode.codehub.service.UserProfileService;
import dev.osmocode.codehub.utils.validator.pagination.PaginationValidator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class DashboardController {

    private final UserProfileService userProfileService;
    private final PaginationValidator paginationValidator;

    public DashboardController(
            UserProfileService userProfileService,
            PaginationValidator paginationValidator) {
        this.userProfileService = userProfileService;
        this.paginationValidator = paginationValidator;
    }

    @GetMapping("/dashboard")
    public ModelAndView getDashboard(
            Authentication authentication,
            @RequestParam(value = "offset", required = false) Optional<Integer> optionalOffset,
            @RequestParam(value = "limit", required = false) Optional<Integer> optionalLimit
    ) {
        var limit = paginationValidator.sanitizeLimit(optionalLimit.orElse(paginationValidator.defaultLimit()));
        var offset = optionalOffset.orElse(0);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dashboard");
        modelAndView.addObject("userProfilePage", userProfileService.findUserByUsernameFetchingFollowings(authentication.getName(), offset, limit));
        return modelAndView;
    }
}
