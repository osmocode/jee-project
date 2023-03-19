package dev.osmocode.codehub.service;

import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.entity.UserScore;
import dev.osmocode.codehub.repository.UserScoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserScoreService {
    private final UserScoreRepository repository;
    private final UserToolsFunction userTools;

    public UserScoreService(UserScoreRepository repository, UserToolsFunction userTools) {
        this.repository = repository;
        this.userTools = userTools;
    }

    @Transactional
    public void performUserScore(String actorName, String targetName, int note) {
        repository.findByAssignerUsernameAndTargetUsername(actorName, targetName).ifPresentOrElse(
                u -> u.setNote(note),
                () -> {
                    User actor = userTools.findByUsernameOrElseThrow(actorName);
                    User target = userTools.findByUsernameOrElseThrow(targetName);
                    if (actor == target) return;
                    if (!actor.getFollowings().contains(target) || !target.getFollowers().contains(actor)) return;
                    repository.save(new UserScore(actor, target, note));
                }
        );
    }

    @Transactional
    public void performUserAddScore(String actorName, String targetName, int note) {
        User actor = userTools.findByUsernameOrElseThrow(actorName);
        User target = userTools.findByUsernameOrElseThrow(targetName);
        if (actor == target) {
            return;
        }
        UserScore userScore = userTools.getScoreWithVerification(actor, target);
        if (null != userScore) {
            return;
        }
        repository.save(new UserScore(actor, target, note));
    }

    @Transactional
    public void performUserDeleteScore(String actorName, String targetName) {
        repository.deleteUserScoreByAssignerUsernameAndTargetUsername(actorName, targetName);
    }

    @Transactional
    public void performUserUpdateScore(String actorName, String targetName, int note) {
        repository.findByAssignerUsernameAndTargetUsername(actorName, targetName).ifPresent(u -> u.setNote(note));
    }
}
