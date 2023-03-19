package dev.osmocode.codehub.entity;

import jakarta.persistence.*;

@Entity(name = "answer_score")
public class AnswerScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    private char vote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "assigner_answer_score",
            joinColumns = @JoinColumn(name = "score_id"),
            inverseJoinColumns = @JoinColumn(name = "assigner_id")
    )
    private User assigner;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "question_answer_score",
            joinColumns = @JoinColumn(name = "score_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id")
    )
    private QuestionAnswer answer;

    public AnswerScore() {}

    public AnswerScore(QuestionAnswer answer, User assigner,char vote) {
        this.answer = answer;
        this.assigner = assigner;
        this.vote = vote;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public char getVote() {
        return vote;
    }

    public void setVote(char vote) {
        this.vote = vote;
    }

    public User getAssigner() {
        return assigner;
    }

    public void setAssigner(User assigner) {
        this.assigner = assigner;
    }

    public QuestionAnswer getAnswer() {
        return answer;
    }

    public void setAnswer(QuestionAnswer answer) {
        this.answer = answer;
    }
}
