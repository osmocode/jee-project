package dev.osmocode.codehub.service;

import dev.osmocode.codehub.dto.QuestionAnswerVoteDto;
import dev.osmocode.codehub.entity.AnswerScore;
import dev.osmocode.codehub.entity.QuestionAnswer;
import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.repository.AnswerScoreRepository;
import dev.osmocode.codehub.repository.QuestionAnswerRepository;
import dev.osmocode.codehub.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AnswerScoreService {

    private final AnswerScoreRepository repository;

    private final QuestionAnswerRepository questionAnswerRepository;

    private final UserRepository userRepository;

    public AnswerScoreService(AnswerScoreRepository repository, UserRepository userRepository, QuestionAnswerRepository questionAnswerRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.questionAnswerRepository = questionAnswerRepository;
    }


    @Transactional
    public void performAnswerAddScore(QuestionAnswerVoteDto questionAnswerVoteDto, String assignerName){
        repository.findByAnswerIdAndAssignerUsername(questionAnswerVoteDto.getAnswerId(), assignerName).ifPresentOrElse(
            a -> a.setVote(questionAnswerVoteDto.getVote()),
            () -> {
                User assigner = userRepository.findByUsername(assignerName).orElse(null);
                if(null == assigner) return;

                QuestionAnswer questionAnswer = questionAnswerRepository.findById(questionAnswerVoteDto.getAnswerId()).orElse(null);
                if(null == questionAnswer) return;

                repository.save(new AnswerScore(questionAnswer, assigner, questionAnswerVoteDto.getVote()));
            }
        );
    }
}
