package dev.osmocode.codehub.utils.validator.pagination;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BasicPaginationValidator implements PaginationValidator {

    private final static int defaultOffset = 0;
    private final static int defaultLimit = 15;

    @Override
    public int sanitizeLimit(Optional<Integer> limit) {
        if (limit.isEmpty()) {
            return defaultLimit;
        }
        int value = limit.get();
        if (value <= 15) {
            return 15;
        } else if (value <= 30) {
            return 30;
        }
        return 50;
    }

    @Override
    public int sanitizeOffset(Optional<Integer> offset) {
        return Math.max(offset.orElse(defaultOffset), 0);
    }

}
