package com.beadmachine.reconciliation.api.domain.common.specification;

import com.beadmachine.reconciliation.api.domain.common.exception.InvalidFilterException;
import com.beadmachine.reconciliation.api.domain.common.model.DateRange;
import com.beadmachine.reconciliation.api.domain.common.model.PageRequest;

import java.util.Objects;

public class ApiSearchSpecification {

    public void validate(
            PageRequest pageRequest,
            DateRange dateRange
    ) {

        Objects.requireNonNull(
                pageRequest,
                "Page request cannot be null."
        );

        Objects.requireNonNull(
                dateRange,
                "Date range cannot be null."
        );

        if (pageRequest.getPage() < 0) {
            throw new InvalidFilterException(
                    "Page index cannot be negative."
            );
        }

        if (pageRequest.getSize() <= 0) {
            throw new InvalidFilterException(
                    "Page size must be greater than zero."
            );
        }

        if (pageRequest.getSize() > PageRequest.MAX_SIZE) {
            throw new InvalidFilterException(
                    "Requested page size exceeds the maximum allowed value."
            );
        }

        if (dateRange.getEndDate().isBefore(dateRange.getStartDate())) {
            throw new InvalidFilterException(
                    "End date must not be before start date."
            );
        }
    }

}