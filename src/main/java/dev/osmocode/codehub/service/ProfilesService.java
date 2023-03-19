package dev.osmocode.codehub.service;

import dev.osmocode.codehub.dto.UserSummaryDto;
import dev.osmocode.codehub.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProfilesService {
    
    private final UserRepository repository;

    public ProfilesService(UserRepository repository) {
        this.repository = repository;
    }
    
    @Transactional
    public Page<UserSummaryDto> getProfilesWithPaginationAndSort(int offset, int limit, String username) {
        return repository.findAllWithPagination(username, PageRequest.of(offset, limit, Sort.by("id").ascending())).map(UserSummaryDto::buildFrom);
    }
}
