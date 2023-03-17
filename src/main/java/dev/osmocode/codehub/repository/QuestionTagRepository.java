package dev.osmocode.codehub.repository;

import dev.osmocode.codehub.entity.QuestionTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionTagRepository extends JpaRepository<QuestionTag, Long> {

    QuestionTag findQuestionTagByName(String name);

    @Query("select t from question_tag t where t.name like %:name%")
    Page<QuestionTag> findQuestionTagByNameLike(String name, Pageable pageable);
}
