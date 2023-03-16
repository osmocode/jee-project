package dev.osmocode.codehub.service;

import dev.osmocode.codehub.entity.QuestionTag;
import dev.osmocode.codehub.repository.QuestionTagRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class QuestionTagService {
    
    private final QuestionTagRepository repository;

    public QuestionTagService(QuestionTagRepository repository) {
        this.repository = repository;
    }
    
    @Transactional
    public void addQuestionTag(QuestionTag questionTag) {
        repository.save(questionTag);
    }

    @Transactional
    public QuestionTag getQuestionTagByTag(String tag) {
        return repository.findQuestionTagByTag(tag);
    }
}
