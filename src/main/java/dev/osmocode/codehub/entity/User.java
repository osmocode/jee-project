package dev.osmocode.codehub.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(max = 50, message = "Username should have at most 50 characters")
    @Column(unique = true)
    private String username;

    @NotNull
//    @Size(max = 50, message = "Password should have at most 50 characters")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authority_id")
    private Authority authority;

    @Pattern(regexp = "^(.+)@(\\S+)$", message = "Wrong email format")
    private String email;

    @Size(max = 190, message = "Description should have at most 190 characters")
    private String description;

    @NotNull
    private Long since;

    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private List<User> follower;

    @ManyToMany(
            fetch = FetchType.EAGER,
            mappedBy = "follower"
    )
    private List<User> following;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "assigner"
    )
    private List<Score> score;

    public User() {
    }

    public User(User user) {
        this.username = user.username;
        this.password = user.password;
        this.email = user.email;
        this.authority = user.authority;
        this.since = user.since;
        this.follower = user.follower;
        this.following = user.following;
        this.description = user.description;
        this.score = user.score;
    }

    public User(String username, String password, String email, Authority authority) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.authority = authority;
        this.since = System.currentTimeMillis();
        this.follower = new ArrayList<>();
        this.following = new ArrayList<>();
        this.description = "";
        this.score = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSince() {
        return since;
    }

    public void setSince(Long since) {
        this.since = since;
    }

    public List<User> getFollower() {
        return follower;
    }

    public void setFollower(List<User> follower) {
        this.follower = follower;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public List<Score> getScore() {
        return score;
    }

    public void setScore(List<Score> score) {
        this.score = score;
    }
}
