package dev.osmocode.codehub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity(name = "question_answer")
public class QuestionAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 30_000)
    private String body;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "answer_user",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "sender_id")
    )
    private User sender;

    @ManyToOne( fetch =  FetchType.LAZY)
    @JoinTable(
            name = "answer_question",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private Question question;

    public QuestionAnswer() {}

    public QuestionAnswer(String body, User sender, Question question) {
        this.body = body;
        this.sender = sender;
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
