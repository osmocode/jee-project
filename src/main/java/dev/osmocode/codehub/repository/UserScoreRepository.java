package dev.osmocode.codehub.repository;

import dev.osmocode.codehub.entity.UserScore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserScoreRepository extends CrudRepository<UserScore, Long> {

    Optional<UserScore> findByAssignerUsernameAndTargetUsername(String assignerUsername, String targetUsername);
    
    void deleteUserScoreByAssignerUsernameAndTargetUsername(String assignerUsername, String targetUsername);
    
}
