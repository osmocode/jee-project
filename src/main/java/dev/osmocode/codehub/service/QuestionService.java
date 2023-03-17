package dev.osmocode.codehub.service;


import dev.osmocode.codehub.dto.QuestionAskedDto;
import dev.osmocode.codehub.entity.Question;
import dev.osmocode.codehub.entity.QuestionTag;
import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.repository.QuestionRepository;
import dev.osmocode.codehub.repository.QuestionTagRepository;
import dev.osmocode.codehub.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class QuestionService {

    private final QuestionRepository repository;
    private final QuestionTagRepository questionTagRepository;
    private final UserRepository userRepository;

    public QuestionService(
            QuestionRepository repository,
            QuestionTagRepository questionTagRepository,
            UserRepository userRepository
    ) {
        this.repository = repository;
        this.questionTagRepository = questionTagRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Question performAskQuestion(QuestionAskedDto questionAskedDto, String username, Set<Long> questionTagsId) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return null;
        }
        if (questionTagsId.size() > 5) {
            return null;
        }
        for (Long id : questionTagsId) {
            if (!questionTagRepository.existsById(id)) {
                return null;
            }
        }
        Set<QuestionTag> questionTagSet = new HashSet<>(questionTagRepository.findAllById(questionTagsId));
        Question question = new Question(questionAskedDto.getTitle(), questionAskedDto.getBody(), optionalUser.get(), questionTagSet);
//        questionTagSet.forEach(questionTag -> questionTag.addQuestion(question));
        return repository.save(question);
    }
}
