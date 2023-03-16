package dev.osmocode.codehub.utils.validator.pagination;

public interface PaginationValidator {
    int sanitizeLimit(int limit);
    
    int defaultLimit();
}
