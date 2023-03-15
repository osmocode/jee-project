package dev.osmocode.codehub.dto;

import dev.osmocode.codehub.entity.User;

public record UserSummaryDto(String username) {
    public static UserSummaryDto buildFrom(User user) {
        return new UserSummaryDto(user.getUsername());
    }
}
