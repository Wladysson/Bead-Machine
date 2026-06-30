package com.beadmachine.reconciliation.api.domain.divergence.model;

public enum DivergenceStatus {

    OPEN,
    ASSIGNED,
    IN_ANALYSIS,
    RESOLVED,
    REJECTED,
    CLOSED;

    public boolean isOpen() {
        return this == OPEN
                || this == ASSIGNED
                || this == IN_ANALYSIS;
    }

    public boolean isClosed() {
        return this == RESOLVED
                || this == REJECTED
                || this == CLOSED;
    }

    public boolean canBeAssigned() {
        return this == OPEN;
    }

}