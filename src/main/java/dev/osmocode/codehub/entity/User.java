package dev.osmocode.codehub.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.*;

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
    private String password;

    @ManyToOne(fetch = FetchType.EAGER) //TODO: LAZY
    @JoinColumn(name = "authority_id")
    private Authority authority;

    @Column(unique = true)
    @Email(message = "Wrong email format")
    private String email;

    @Size(max = 190, message = "Description should have at most 190 characters")
    private String about;

    @NotNull
    private Long since;

    @ManyToMany(
            fetch = FetchType.EAGER //TODO: LAZY
    )
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")

    )
    private List<User> followers;

    @ManyToMany(
            fetch = FetchType.EAGER, //TODO: LAZY
            mappedBy = "followers" //TODO: think about this
    )
    private List<User> followings;

    @OneToMany(
            fetch = FetchType.EAGER, //TODO: LAZY
            mappedBy = "target"
    )
    private List<UserScore> attributedScores;


    @OneToMany(
            fetch = FetchType.EAGER, //TODO: LAZY
            mappedBy = "assigner"
    )
    private List<UserScore> distributedScore;

    /* constructors */

    public User() {
    }

    public User(User user) {
        this.username = user.username;
        this.password = user.password;
        this.email = user.email;
        this.authority = user.authority;
        this.since = user.since;
        this.followers = user.followers;
        this.followings = user.followings;
        this.about = user.about;
        this.attributedScores = new ArrayList<>();
        this.distributedScore = new ArrayList<>();
    }

    public User(String username, String password, String email, Authority authority) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.authority = authority;
        this.since = System.currentTimeMillis();
        this.followers = new ArrayList<>();
        this.followings = new ArrayList<>();
        this.about = "";
        this.attributedScores = new ArrayList<>();
        this.distributedScore = new ArrayList<>();
    }

    /* social */

    public void follow(User following) {
        this.followings.add(following);
        following.addFollower(this);
    }

    private void addFollower(User follower) {
        this.followers.add(follower);
    }

    public void unfollow(User following) {
        this.followings.remove(following);
        following.removeFollower(this);
    }

    private void removeFollower(User follower) {
        this.followers.remove(follower);
    }

    /* getters and setters */

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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Long getSince() {
        return since;
    }

    public void setSince(Long since) {
        this.since = since;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowings() {
        return followings;
    }

    public void setFollowings(List<User> followings) {
        this.followings = followings;
    }

    public List<UserScore> getAttributedScores() {
        return attributedScores;
    }

    public void setAttributedScores(List<UserScore> attributedScores) {
        this.attributedScores = attributedScores;
    }

    public List<UserScore> getDistributedScore() {
        return distributedScore;
    }

    public void setDistributedScore(List<UserScore> distributedScore) {
        this.distributedScore = distributedScore;
    }
}
