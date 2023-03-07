package dev.osmocode.codehub.utils;

public enum Role {
    USER {
        @Override
        public String toString() {
            return "ROLE_USER";
        }
    },
    ADMIN {
        @Override
        public String toString() {
            return "ROLE_ADMIN";
        }
    }
}