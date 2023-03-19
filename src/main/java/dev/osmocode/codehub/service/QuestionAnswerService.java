package dev.osmocode.codehub.service;

import dev.osmocode.codehub.dto.QuestionAnswerDto;
import dev.osmocode.codehub.entity.Question;
import dev.osmocode.codehub.entity.QuestionAnswer;
import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.repository.QuestionAnswerRepository;
import dev.osmocode.codehub.repository.QuestionRepository;
import dev.osmocode.codehub.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class QuestionAnswerService {

    private final QuestionAnswerRepository repository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    public QuestionAnswerService(
            QuestionAnswerRepository repository,
            UserRepository userRepository,
            QuestionRepository questionRepository
    ) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public Set<QuestionAnswer> findAnswersById(long id){
        return repository.findQuestionAnswerByQuestionId(id);
    }

    @Transactional
    public void performAnswerQuestion(QuestionAnswerDto questionAnswerDto, String username, long id) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return;

        Question question = questionRepository.findById(id).orElse(null);
        if (question == null) return;

        repository.save(new QuestionAnswer(
                questionAnswerDto.getBody(), user, question
        ));
    }
}
