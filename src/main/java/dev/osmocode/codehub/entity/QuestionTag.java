package dev.osmocode.codehub.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "question_tag")
public class QuestionTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(
            fetch = FetchType.LAZY,
            mappedBy = "tags"
    )
    private Set<Question> questions;

    public QuestionTag() {
    }

    public QuestionTag(String name) {

        this.name = name;
        this.questions = new HashSet<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}
