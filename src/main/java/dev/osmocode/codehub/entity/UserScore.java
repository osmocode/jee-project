package dev.osmocode.codehub.entity;

import jakarta.persistence.*;

@Entity(name = "user_score")
public class UserScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private int note;

    @ManyToOne(
            //TODO: fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "assigner_user_score",
            joinColumns = @JoinColumn(name = "score_id"),
            inverseJoinColumns = @JoinColumn(name = "assigner_id")
    )
    private User assigner;

    @ManyToOne(
            //TODO: fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "target_user_score",
            joinColumns = @JoinColumn(name = "score_id"),
            inverseJoinColumns = @JoinColumn(name = "target_id")
    )
    private User target;

    // constructors

    public UserScore() {
    }

    public UserScore(User assigner, User target, int note) {
        this.note = note;
        this.assigner = assigner;
        this.target = target;
    }

    // getters and setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public User getAssigner() {
        return assigner;
    }

    public void setAssigner(User assigner) {
        this.assigner = assigner;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }
}