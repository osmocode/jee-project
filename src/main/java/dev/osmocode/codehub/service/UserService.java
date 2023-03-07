package dev.osmocode.codehub.service;


import dev.osmocode.codehub.repository.UserRepository;
import dev.osmocode.codehub.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Optional<User> findUserByUserName(String userName) {
        return repository.findByUsername(userName);
    }

    @Transactional
    public User saveUser(User user) {
        Optional<User> optionalUser = repository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            return null;
        }
        return repository.save(user);
    }
}
