package dev.osmocode.codehub.utils;

public class UserCreationResult {

    public enum Field {
        USERNAME,
        EMAIL
    }

    private final Field field;
    private final boolean failed;

    public UserCreationResult(boolean failed) {
        this.failed = failed;
        this.field = null;
    }
    
    public UserCreationResult(boolean failed, Field field) {
        this.failed = failed;
        this.field = field;
    }

    public boolean isFailed() {
        return failed;
    }

    public Field getField() {
        return field;
    }
}
