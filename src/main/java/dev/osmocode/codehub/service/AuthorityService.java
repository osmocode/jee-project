package dev.osmocode.codehub.service;

import dev.osmocode.codehub.entity.Authority;
import dev.osmocode.codehub.repository.AuthorityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

    private final AuthorityRepository repository;

    public AuthorityService(AuthorityRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Authority saveAuthority(Authority authority) {
        return repository.save(authority);
    }

    @Transactional
    public Authority findAuthorityByName(String name) {
        return repository.findByAuthority(name);
    }
}
