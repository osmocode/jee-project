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
    public void performAnswerAddScore(QuestionAnswerVoteDto questionAnswerVoteDto, String assignerName, String senderName){
        repository.findByAnswerIdAndAssignerUsername(questionAnswerVoteDto.getAnswerId(), assignerName).ifPresentOrElse(
            a -> {
                if(questionAnswerVoteDto.getVote().equals("+")) a.setVote(1);
                if(questionAnswerVoteDto.getVote().equals("-")) a.setVote(-1);
            },
            () -> {
                User assigner = userRepository.findByUsername(assignerName).orElse(null);
                if(null == assigner) return;

                User sender = userRepository.findByUsername(senderName).orElse(null);
                if (null == sender) return;

                if(assigner == sender) return;

                QuestionAnswer questionAnswer = questionAnswerRepository.findById(questionAnswerVoteDto.getAnswerId()).orElse(null);
                if(null == questionAnswer) return;

                if(questionAnswerVoteDto.getVote().equals("+")) repository.save(new AnswerScore(questionAnswer, assigner, 1));
                if(questionAnswerVoteDto.getVote().equals("-")) repository.save(new AnswerScore(questionAnswer, assigner, -1));
            }
        );
    }
}
