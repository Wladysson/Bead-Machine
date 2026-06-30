package com.beadmachine.reconciliation.api.domain.common.model;

public enum SortDirection {

    ASC,
    DESC;

    public boolean isAscending() {
        return this == ASC;
    }

    public boolean isDescending() {
        return this == DESC;
    }

    public static SortDirection from(String value) {

        if (value == null || value.isBlank()) {
            return ASC;
        }

        return SortDirection.valueOf(value.trim().toUpperCase());
    }

}