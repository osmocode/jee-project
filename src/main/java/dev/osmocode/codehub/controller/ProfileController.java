package dev.osmocode.codehub.controller;

import dev.osmocode.codehub.dto.UserProfileDto;
import dev.osmocode.codehub.dto.UserSummaryDto;
import dev.osmocode.codehub.service.*;
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

    private final UserScoreService userScoreService;
    private final UserFollowService userFollowService;
    private final ProfilesService profilesService;
    private final PaginationValidator paginationValidator;
    private final UserProfileService userProfileService;

    public ProfileController(
            UserScoreService userScoreService,
            UserFollowService userFollowService,
            ProfilesService profilesService,
            PaginationValidator paginationValidator,
            UserProfileService userProfileService) {
        this.userScoreService = userScoreService;
        this.userFollowService = userFollowService;
        this.profilesService = profilesService;
        this.paginationValidator = paginationValidator;
        this.userProfileService = userProfileService;
    }

    @GetMapping("/profiles")
    public ModelAndView getProfiles(
            @RequestParam(value = "offset", required = false) Optional<Integer> optionalOffset,
            @RequestParam(value = "limit", required = false) Optional<Integer> optionalLimit
    ) {
        var limit = paginationValidator.sanitizeLimit(optionalLimit);
        var offset = paginationValidator.sanitizeOffset(optionalOffset);
        Page<UserSummaryDto> profilesWithPagination = profilesService.getProfilesWithPaginationAndSort(offset, limit);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profiles");
        modelAndView.addObject("profilesPage", profilesWithPagination);
        return modelAndView;
    }

    @GetMapping("/profile/{username}")
    public ModelAndView getProfile(@PathVariable String username) {
        UserProfileDto user = userProfileService.getUserProfile(username);
        if (user == null) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("error");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        modelAndView.addObject("userDto", user);
        return modelAndView;
    }

    @GetMapping("/profile/{username}/followers")
    public ModelAndView getUserFollowers(
            @PathVariable String username,
            @RequestParam(value = "offset", required = false) Optional<Integer> optionalOffset,
            @RequestParam(value = "limit", required = false) Optional<Integer> optionalLimit
    ) {
        var limit = paginationValidator.sanitizeLimit(optionalLimit);
        var offset = paginationValidator.sanitizeOffset(optionalOffset);

        Page<UserSummaryDto> followersPage = userProfileService.findUserByUsernameFetchingFollowers(username, offset, limit);
        if (followersPage == null) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("error");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("followers");
        modelAndView.addObject("followersPage", followersPage);
        modelAndView.addObject("currentProfileName",username);
        return modelAndView;
    }


    @GetMapping("/profile/{username}/followings")
    public ModelAndView getUserFollowings(
            @PathVariable String username,
            @RequestParam(value = "offset", required = false) Optional<Integer> optionalOffset,
            @RequestParam(value = "limit", required = false) Optional<Integer> optionalLimit
    ) {
        var limit = paginationValidator.sanitizeLimit(optionalLimit);
        var offset = paginationValidator.sanitizeOffset(optionalOffset);

        Page<UserSummaryDto> followingsPage = userProfileService.findUserByUsernameFetchingFollowings(username, offset, limit);
        if (followingsPage == null) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("error");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("followings");
        modelAndView.addObject("followingsPage", followingsPage);
        modelAndView.addObject("currentProfileName",username);
        return modelAndView;
    }

    @PostMapping("/profile/{username}/follow")
    public ModelAndView postFollow(
            Authentication authentication,
            @PathVariable String username
    ) {
        if (null == authentication) {
            return generateError(username,
                    "User not authenticated can't follow another user");
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
                    "User not authenticated can't unfollow another user");
        }
        userFollowService.performUserUnfollow(authentication.getName(), username);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/profile/" + username);
        return modelAndView;
    }

    @PostMapping("/profile/{username}/note")
    public ModelAndView postNote(
            Authentication authentication,
            @PathVariable String username,
            @RequestParam(name = "note") int note
    ) {
        if (null == authentication) {
            return generateError(username,
                    "User not authenticated can't note another user");
        }
        if (note < 1 || note > 5) {
            return generateError(username,
                    "Note should be between 1 and 5");
        }
        if (authentication.getName().equals(username)) {
            return generateError(username,
                    "Can't follow yourself");
        }
        userScoreService.performUserScore(authentication.getName(), username, note);
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
                    "User not authenticated can't delete note");
        }
        userScoreService.performUserDeleteScore(authentication.getName(), username);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/profile/" + username);
        return modelAndView;
    }

    private ModelAndView generateError(String username, String message) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("timestamp", new Date().toString());
        modelAndView.addObject("path", "/profile/" + username);
        modelAndView.addObject("error", message);
        modelAndView.addObject("status", HttpStatus.BAD_REQUEST);
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        return modelAndView;
    }
}
