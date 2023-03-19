package dev.osmocode.codehub.service;

import dev.osmocode.codehub.entity.AnswerScore;
import dev.osmocode.codehub.entity.QuestionAnswer;
import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.repository.AnswerScoreRepository;
import dev.osmocode.codehub.repository.QuestionAnswerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AnswerScoreService {

    private final AnswerScoreRepository repository;

    private final QuestionAnswerRepository questionAnswerRepository;

    private final UserToolsFunction userTools;

    public AnswerScoreService(AnswerScoreRepository repository, UserToolsFunction userTools, QuestionAnswerRepository questionAnswerRepository) {
        this.repository = repository;
        this.userTools = userTools;
        this.questionAnswerRepository = questionAnswerRepository;
    }


    @Transactional
    public void performAnswerAddScore(long id, String assignerName, char vote){
        repository.findByAnswerIdAndAssignerUsername(id, assignerName).ifPresentOrElse(
            a -> a.setVote(vote),
            () -> {
                User assigner = userTools.findByUsernameOrElseThrow(assignerName);
                QuestionAnswer questionAnswer = questionAnswerRepository.findQuestionAnswerById(id);
                repository.save(new AnswerScore(questionAnswer, assigner, vote));
            }
        );
    }


    @Transactional
    public void performAnswerDeleteScore(long id, String assignerName){
        repository.deleteAnswerScoreByAnswerIdAndAssignerUsername(id, assignerName);
    }

    @Transactional
    public void performAnswerUpdateScore(long id, String assignerName, char vote){
        repository.findByAnswerIdAndAssignerUsername(id, assignerName).ifPresent(a -> a.setVote(vote));
    }

}
