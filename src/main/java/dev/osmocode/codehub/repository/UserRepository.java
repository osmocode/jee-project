package dev.osmocode.codehub.repository;

import dev.osmocode.codehub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsUserByEmail(String email);

    @Query("SELECT u FROM users u LEFT JOIN FETCH u.followers WHERE u.username=:username")
    Optional<User> findUserByUsernameFetchingFollowings(String username);

    @Query("SELECT u FROM users u LEFT JOIN FETCH u.followers LEFT JOIN FETCH u.followings LEFT JOIN FETCH u.attributedScores WHERE u.username=:username")
    Optional<User> findUserFetchingProfile(String username);
}
