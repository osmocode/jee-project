package dev.osmocode.codehub.repository;

import dev.osmocode.codehub.entity.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {

    @Query("SELECT a FROM question_answer a WHERE a.question.id=:id")
    Set<QuestionAnswer> findQuestionAnswerById(long id);

}
