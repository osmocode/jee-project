package dev.osmocode.codehub.repository;

import dev.osmocode.codehub.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsUserByEmail(String email);

    boolean existsUserByUsername(String username);

    Page<User> findUserByFollowersUsername(String username, Pageable pageable);

    Page<User> findUserByFollowingsUsername(String username, Pageable pageable);

    @Query("SELECT u FROM users u LEFT JOIN FETCH u.followers LEFT JOIN FETCH u.followings LEFT JOIN FETCH u.attributedScores WHERE u.username=:username")
    Optional<User> findUserFetchingProfile(String username);
}
