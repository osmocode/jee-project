package dev.osmocode.codehub.service;

import dev.osmocode.codehub.entity.QuestionTag;
import dev.osmocode.codehub.repository.QuestionTagRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class QuestionTagService {
    
    private final QuestionTagRepository repository;

    public QuestionTagService(QuestionTagRepository repository) {
        this.repository = repository;
    }
    
    @Transactional
    public void addQuestionTag(QuestionTag questionTag) {
        questionTag.setName(questionTag.getName().toLowerCase());
        if(repository.findQuestionTagByName(questionTag.getName()) == null) {
            repository.save(questionTag);
        }
    }
    
    @Transactional
    public Page<QuestionTag> getTagsBySearch(String search, int limit, int offset) {
        return repository.findQuestionTagByNameLike(search.toLowerCase(), PageRequest.of(offset, limit, Sort.by("id").ascending()));
    }
}
