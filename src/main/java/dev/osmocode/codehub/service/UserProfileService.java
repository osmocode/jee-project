package dev.osmocode.codehub.service;

import dev.osmocode.codehub.dto.UserProfileDto;
import dev.osmocode.codehub.dto.UserSummaryDto;
import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.repository.UserRepository;
import dev.osmocode.codehub.utils.SinceFormater;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        var oprtionalUser = repository.findUserFetchingProfile(username);
        if (oprtionalUser.isEmpty())
        {
            return null;
        }

                //.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserProfileDto.buildFrom(oprtionalUser.get(), sinceFormater);
    }

    @Transactional
    public Page<UserSummaryDto> findUserByUsernameFetchingFollowings(String username, int offset, int limit) {
        var user = repository.findUserByFollowersUsername(username, PageRequest.of(offset, limit, Sort.by("id").ascending()));
        return user.map(UserSummaryDto::buildFrom);
    }
}
