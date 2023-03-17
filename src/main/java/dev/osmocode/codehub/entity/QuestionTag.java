package dev.osmocode.codehub.entity;

import jakarta.persistence.*;

@Entity(name="question_tags")
public class QuestionTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(unique = true)
    private String name;
    
    public QuestionTag() {}
    
    public QuestionTag(String name) {
        this.name = name;
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
}
