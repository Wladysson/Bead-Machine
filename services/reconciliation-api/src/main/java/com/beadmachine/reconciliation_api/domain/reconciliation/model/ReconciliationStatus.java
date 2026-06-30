package com.beadmachine.reconciliation.api.domain.reconciliation.model;

public enum ReconciliationStatus {

    PENDING,
    PROCESSING,
    MATCHED,
    PARTIALLY_MATCHED,
    DIVERGENT,
    MANUAL_REVIEW,
    REPROCESSED,
    FAILED;

    public boolean isFinished() {

        return this == MATCHED
                || this == PARTIALLY_MATCHED
                || this == DIVERGENT
                || this == FAILED;
    }

    public boolean requiresManualReview() {
        return this == MANUAL_REVIEW;
    }

}