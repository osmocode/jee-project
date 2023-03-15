package dev.osmocode.codehub.dto;

import dev.osmocode.codehub.entity.UserScore;
import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.utils.SinceFormater;


public record UserProfileDto(
        String username,
        String email,
        String about,
        String since,
        int followers,
        int following,
        int reviews, // nb note
        double grade // average note
) {

    public static UserProfileDto buildFrom(User user, SinceFormater sinceFormater) {
        double grade = user.getAttributedScores().stream().mapToInt(UserScore::getNote).average().orElse(0.0);
        grade = grade * 100;
        grade = (int) grade; // safe, grade should be between 0 and 5
        grade = grade / 100;
        return new UserProfileDto(
                user.getUsername(),
                user.getEmail(),
                user.getAbout(),
                sinceFormater.formatSince(user.getSince()),
                user.getFollowers().size(),
                user.getFollowings().size(),
                user.getAttributedScores().size(),
                grade
        );
    }
}
