package dev.osmocode.codehub.controller;

import dev.osmocode.codehub.dto.UserProfileDto;
import dev.osmocode.codehub.dto.UserSummaryDto;
import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.service.ProfilesService;
import dev.osmocode.codehub.service.UserFollowService;
import dev.osmocode.codehub.service.UserScoreService;
import dev.osmocode.codehub.service.UserService;
import dev.osmocode.codehub.utils.validator.pagination.PaginationValidator;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Optional;

@Controller
public class ProfileController {

    private final UserService userService;
    private final UserScoreService userScoreService;
    private final UserFollowService userFollowService;
    private final ProfilesService profilesService;
    private final PaginationValidator paginationValidator;

    public ProfileController(
            UserService userService,
            UserScoreService userScoreService,
            UserFollowService userFollowService,
            ProfilesService profilesService,
            PaginationValidator paginationValidator
    ) {
        this.userService = userService;
        this.userScoreService = userScoreService;
        this.userFollowService = userFollowService;
        this.profilesService = profilesService;
        this.paginationValidator = paginationValidator;
    }

    //TODO: Add pagination
    @GetMapping("/profiles")
    public ModelAndView getProfiles(
            @RequestParam(value = "offset", required = false) Optional<Integer> optionalOffset,
            @RequestParam(value = "limit", required = false) Optional<Integer> optionalLimit
    ) {
        var limit = paginationValidator.sanitizeLimit(optionalLimit.orElse(paginationValidator.defaultLimit()));
        var offset = optionalOffset.orElse(0);
        Page<UserSummaryDto> profilesWithPagination = profilesService.getProfilesWithPaginationAndSort(offset, limit);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profiles");
        modelAndView.addObject("profilesPage", profilesWithPagination);
        return modelAndView;
    }

    @GetMapping("/profile/{username}")
    public ModelAndView getProfile(@PathVariable String username) {
        Optional<User> optionalUser = userService.findUserByUserName(username);
        if (optionalUser.isEmpty()) {
            return generateError(username, "User does not exists", HttpStatus.NOT_FOUND);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        modelAndView.addObject("userDto", UserProfileDto.buildFrom(optionalUser.get()));
        return modelAndView;
    }

    @PostMapping("/profile/{username}/follow")
    public ModelAndView postFollow(
            Authentication authentication,
            @PathVariable String username
    ) {
        if (null == authentication) {
            return generateError(username,
                    "User not authenticated can't follow another user",
                    HttpStatus.BAD_REQUEST);
        }
        userFollowService.performUserFollow(authentication.getName(), username);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/profile/" + username);
        return modelAndView;
    }

    @PostMapping("/profile/{username}/unfollow")
    public ModelAndView postUnfollow(
            Authentication authentication,
            @PathVariable String username
    ) {
        if (null == authentication) {
            return generateError(username,
                    "User not authenticated can't unfollow another user",
                    HttpStatus.BAD_REQUEST);
        }
        userFollowService.performUserUnfollow(authentication.getName(), username);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/profile/" + username);
        return modelAndView;
    }

    @PostMapping("/profile/{username}/score")
    public ModelAndView postScore(
            Authentication authentication,
            @PathVariable String username,
            @RequestParam(name = "note") int note
    ) {
        if (null == authentication) {
            return generateError(username,
                    "User not authenticated can't note another user",
                    HttpStatus.BAD_REQUEST);
        }
        if (note < 1 || note > 5) {
            return generateError(username,
                    "Note should be between 1 and 5",
                    HttpStatus.BAD_REQUEST);
        }
        if (authentication.getName().equals(username)) {
            return generateError(username,
                    "Can't follow yourself",
                    HttpStatus.BAD_REQUEST);
        }
        userScoreService.performUserAddScore(authentication.getName(), username, note);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/profile/" + username);
        return modelAndView;
    }


    @PostMapping("/profile/{username}/delete-score")
    public ModelAndView postDeleteScore(
            Authentication authentication,
            @PathVariable String username
    ) {
        if (null == authentication) {
            return generateError(username,
                    "User not authenticated can't delete note",
                    HttpStatus.BAD_REQUEST);
        }
        userScoreService.performUserDeleteScore(authentication.getName(), username);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/profile/" + username);
        return modelAndView;
    }

    @PostMapping("/profile/{username}/update-score")
    public ModelAndView postUpdateScore(
            Authentication authentication,
            @PathVariable String username,
            @RequestParam(name = "note") int note
    ) {
        if (null == authentication) {
            return generateError(username,
                    "User not authenticated can't update note",
                    HttpStatus.BAD_REQUEST);
        }
        if (note < 1 || note > 5) {
            return generateError(username,
                    "Note should be between 1 and 5",
                    HttpStatus.BAD_REQUEST);
        }
        userScoreService.performUserUpdateScore(authentication.getName(), username, note);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/profile/" + username);
        return modelAndView;
    }

    private ModelAndView generateError(String username, String message, HttpStatus status) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("timestamp", new Date().toString());
        modelAndView.addObject("path", "/profile/" + username);
        modelAndView.addObject("error", message);
        modelAndView.addObject("status", status);
        modelAndView.setStatus(status);
        return modelAndView;
    }
}
