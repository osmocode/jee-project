package dev.osmocode.codehub.dto;

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

    private int score;

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
