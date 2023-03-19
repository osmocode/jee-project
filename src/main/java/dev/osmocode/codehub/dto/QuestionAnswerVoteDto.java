package dev.osmocode.codehub.dto;

import dev.osmocode.codehub.entity.VoteType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import org.springframework.lang.NonNull;

public class QuestionAnswerVoteDto {

    @NonNull
    private int questionId;

    @NonNull
    private int answerId;

    @NonNull
    @Size(min = 1, max = 1)
    private String vote;

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

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}
