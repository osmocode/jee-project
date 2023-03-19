package dev.osmocode.codehub.entity;

public enum VoteType {
    UP,
    DOWN;

    public static VoteType fromString(String type) {
        if (type.equals("+")) return VoteType.UP;
        if (type.equals("-")) return VoteType.DOWN;
        return null;
    }
}
