package dev.osmocode.codehub.repository;

import dev.osmocode.codehub.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM questions q WHERE q.title LIKE %:title%")
    Page<Question> findQuestionByTitle(String title, Pageable pageable);

}
