package com.beadmachine.reconciliation.api.domain.common.model;

import java.util.Objects;

public final class PageRequest {

    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_SIZE = 20;
    public static final int MAX_SIZE = 500;

    private final int page;
    private final int size;
    private final String sortBy;
    private final SortDirection direction;

    private PageRequest(
            int page,
            int size,
            String sortBy,
            SortDirection direction
    ) {

        if (page < 0) {
            throw new IllegalArgumentException("Page index cannot be negative.");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero.");
        }

        if (size > MAX_SIZE) {
            throw new IllegalArgumentException(
                    "Page size cannot be greater than " + MAX_SIZE
            );
        }

        this.page = page;
        this.size = size;
        this.sortBy = sortBy == null ? "createdAt" : sortBy;
        this.direction = direction == null ? SortDirection.ASC : direction;
    }

    public static PageRequest of(
            int page,
            int size,
            String sortBy,
            SortDirection direction
    ) {
        return new PageRequest(page, size, sortBy, direction);
    }

    public static PageRequest defaultPage() {
        return new PageRequest(
                DEFAULT_PAGE,
                DEFAULT_SIZE,
                "createdAt",
                SortDirection.DESC
        );
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public SortDirection getDirection() {
        return direction;
    }

    public int getOffset() {
        return page * size;
    }

    public PageRequest next() {
        return new PageRequest(
                page + 1,
                size,
                sortBy,
                direction
        );
    }

    public PageRequest previous() {

        if (page == 0) {
            return this;
        }

        return new PageRequest(
                page - 1,
                size,
                sortBy,
                direction
        );
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof PageRequest that)) {
            return false;
        }

        return page == that.page
                && size == that.size
                && Objects.equals(sortBy, that.sortBy)
                && direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                page,
                size,
                sortBy,
                direction
        );
    }

}