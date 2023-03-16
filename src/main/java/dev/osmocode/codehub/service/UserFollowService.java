package dev.osmocode.codehub.service;

import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.entity.UserScore;
import dev.osmocode.codehub.repository.UserScoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserFollowService {
    
    private final UserScoreRepository userScoreRepository;
    private final UserToolsFunction userTools;
    
    
    public UserFollowService(UserScoreRepository userScoreRepository, UserToolsFunction userTools) {
        this.userScoreRepository = userScoreRepository;
        this.userTools = userTools;
    }

    @Transactional
    public void performUserFollow(String actorName, String targetName) {
        User actor = userTools.findByUsernameOrElseThrow(actorName);
        User target = userTools.findByUsernameOrElseThrow(targetName);
        if (actor == target) {
            return;
        }
        if (actor.getFollowings().contains(target)) {
            return;
        }
        actor.follow(target);
    }

    @Transactional
    public void performUserUnfollow(String actorName, String targetName) {
        User actor = userTools.findByUsernameOrElseThrow(actorName);
        User target = userTools.findByUsernameOrElseThrow(targetName);
        if (actor == target) {
            return;
        }
        if (!actor.getFollowings().contains(target)) {
            return;
        }
        UserScore userScore = userTools.getScoreWithVerification(actor, target);
        if (null != userScore) {
            userScoreRepository.delete(userScore);
        }
        actor.unfollow(target);
    }
    
}
