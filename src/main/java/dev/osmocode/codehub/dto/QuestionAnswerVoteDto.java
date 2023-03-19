package dev.osmocode.codehub.dto;

import dev.osmocode.codehub.entity.VoteType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.lang.NonNull;

public class QuestionAnswerVoteDto {

    @NonNull
    private int questionId;

    @NonNull
    private int answerId;

    @Enumerated(EnumType.STRING)
    private VoteType vote;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public VoteType getVote() {
        return vote;
    }

    public void setVote(VoteType vote) {
        this.vote = vote;
    }
}
