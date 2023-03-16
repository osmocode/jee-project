package dev.osmocode.codehub.utils.validator.pagination;

import org.springframework.stereotype.Component;

@Component
public class BasicPaginationValidator implements PaginationValidator {
    
    @Override
    public int sanitizeLimit(int limit) {
        if (limit <= 15) {
            return 15;
        } else if (limit <= 30) {
            return 30;
        }
        return 50;
    }

    @Override
    public int defaultLimit() {
        return 15;
    }

}
