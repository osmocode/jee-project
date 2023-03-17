package dev.osmocode.codehub.utils.validator.pagination;

import java.util.Optional;

public interface PaginationValidator {
    int sanitizeLimit(Optional<Integer> limit);
    int sanitizeOffset(Optional<Integer> offset);
}
