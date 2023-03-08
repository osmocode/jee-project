package dev.osmocode.codehub;


import dev.osmocode.codehub.entity.Score;
import dev.osmocode.codehub.entity.User;

import java.text.DateFormat;
import java.util.Date;

public record UserDto(
        String username,
        String email,
        String description,
        String since,
        int followers,
        int following,
        int reviews, // nb note
        double grade // average
) {

    public static UserDto buildFrom(User user) {
        
        double grade = user.getScore().stream().mapToInt(Score::getScore).average().orElse(0);
        grade = grade * 100;
        grade = (int) grade;
        grade = grade / 100;
        Date date = new Date(user.getSince());
        return new UserDto(
                user.getUsername(),
                user.getEmail(),
                user.getDescription(),
                date.toString(),
                user.getFollower().size(),
                user.getFollowing().size(),
                user.getScore().size(),
                grade
        );
    }
}
