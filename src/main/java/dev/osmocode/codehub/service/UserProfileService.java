package dev.osmocode.codehub.service;

import dev.osmocode.codehub.dto.UserProfileDto;
import dev.osmocode.codehub.dto.UserSummaryDto;
import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.repository.UserRepository;
import dev.osmocode.codehub.utils.SinceFormater;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {

    private final UserRepository repository;
    private final SinceFormater sinceFormater;

    public UserProfileService(
            UserRepository repository,
            SinceFormater sinceFormater) {
        this.repository = repository;
        this.sinceFormater = sinceFormater;
    }

    @Transactional
    public UserProfileDto getUserProfile(String username) {
        User user = repository.findUserFetchingProfile(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserProfileDto.buildFrom(user, sinceFormater);
    }

    @Transactional
    public List<UserSummaryDto> findUserByUsernameFetchingFollowings(String username) {
        var user = repository.findUserByUsernameFetchingFollowings(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getFollowings()
                .stream()
                .map(UserSummaryDto::buildFrom)
                .toList();
    }
}
