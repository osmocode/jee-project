package dev.osmocode.codehub.service;

import dev.osmocode.codehub.dto.UserProfileDto;
import dev.osmocode.codehub.dto.UserSummaryDto;
import dev.osmocode.codehub.repository.UserRepository;
import dev.osmocode.codehub.utils.SinceFormater;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        var optionalUser = repository.findUserFetchingProfile(username);
        return optionalUser.map(user -> UserProfileDto.buildFrom(user, sinceFormater)).orElse(null);
    }

    @Transactional
    public Page<UserSummaryDto> findUserByUsernameFetchingFollowings(String username, int offset, int limit) {
        if(!repository.existsUserByUsername(username)) {
            return null;
        }
        var user = repository.findUserByFollowersUsername(username, PageRequest.of(offset, limit, Sort.by("id").ascending()));
        return user.map(UserSummaryDto::buildFrom);
    }

    @Transactional
    public Page<UserSummaryDto> findUserByUsernameFetchingFollowers(String username, int offset, int limit) {
        if(!repository.existsUserByUsername(username)) {
            return null;
        }
        var user = repository.findUserByFollowingsUsername(username, PageRequest.of(offset, limit, Sort.by("id").ascending()));
        return user.map(UserSummaryDto::buildFrom);
    }
}
