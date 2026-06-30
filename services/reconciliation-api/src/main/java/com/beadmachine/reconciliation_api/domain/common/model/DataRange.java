package com.beadmachine.reconciliation.api.domain.common.model;

import java.time.LocalDate;
import java.util.Objects;

public final class DateRange {

    private final LocalDate startDate;
    private final LocalDate endDate;

    private DateRange(
            LocalDate startDate,
            LocalDate endDate
    ) {

        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null.");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("End date cannot be null.");
        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException(
                    "End date cannot be before start date."
            );
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static DateRange of(
            LocalDate startDate,
            LocalDate endDate
    ) {
        return new DateRange(startDate, endDate);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean contains(LocalDate date) {

        if (date == null) {
            return false;
        }

        return !date.isBefore(startDate)
                && !date.isAfter(endDate);
    }

    public long totalDays() {
        return startDate.until(endDate).getDays();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof DateRange that)) {
            return false;
        }

        return Objects.equals(startDate, that.startDate)
                && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                startDate,
                endDate
        );
    }

}