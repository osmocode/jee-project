package dev.osmocode.codehub.service;

import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.entity.UserScore;
import dev.osmocode.codehub.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserToolsFunction {
    
    private final UserRepository userRepository;

    public UserToolsFunction(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsernameOrElseThrow(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /*
     * return null if actor doesn't score target
     * */
    public UserScore getScoreWithVerification(User actor, User target) {
        if (actor == target) {
            return null;
        }
        if (!actor.getFollowings().contains(target) || !target.getFollowers().contains(actor)) {
            return null;
        }
        return target.getAttributedScores().stream().filter(s -> s.getAssigner().equals(actor)).findFirst().orElse(null);
    }
}
