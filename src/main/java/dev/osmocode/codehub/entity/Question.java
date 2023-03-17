package dev.osmocode.codehub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 150)
    private String title;

    @Size(max = 30_000)
    private String body;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "question_user",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "interviewer_id")
    )
    private User interviewer;

    @ManyToMany(
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "attributed_question_tags",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<QuestionTag> tags;

//    @OneToMany
//    Set<Answer> answers;

//    @OneToMany
//    Set<QuestionComment> comments;

    public Question() {
    }

    public Question(String title, String body, User interviewer, Set<QuestionTag> tags) {
        this.title = title;
        this.body = body;
        this.interviewer = interviewer;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(User interviewer) {
        this.interviewer = interviewer;
    }

    public Set<QuestionTag> getTags() {
        return tags;
    }

    public void setTags(Set<QuestionTag> tags) {
        this.tags = tags;
    }
}