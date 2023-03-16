package dev.osmocode.codehub.repository;

import dev.osmocode.codehub.entity.QuestionTag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionTagRepository extends CrudRepository<QuestionTag, Long> {
    
    QuestionTag findQuestionTagByTag(String tag);
}
