package dev.osmocode.codehub.repository;

import dev.osmocode.codehub.entity.AnswerScore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerScoreRepository  extends CrudRepository<AnswerScore, Long> {

    Optional<AnswerScore> findByAnswerIdAndAssignerUsername(long id, String assignerUsername);

}
