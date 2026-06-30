package com.beadmachine.reconciliation.api.domain.reconciliation.model;

import com.beadmachine.reconciliation.api.domain.common.exception.BusinessRuleViolationException;

import java.time.Instant;
import java.util.Objects;

public class Reconciliation {

    private final ReconciliationId id;
    private final ReconciliationType type;

    private ReconciliationStatus status;

    private final Instant createdAt;
    private Instant updatedAt;
    private Instant completedAt;

    private MatchSummary matchSummary;
    private ReconciliationSnapshot snapshot;

    private Reconciliation(
            ReconciliationId id,
            ReconciliationType type,
            ReconciliationStatus status,
            MatchSummary matchSummary,
            ReconciliationSnapshot snapshot,
            Instant createdAt,
            Instant updatedAt,
            Instant completedAt
    ) {

        this.id = Objects.requireNonNull(id, "Reconciliation id cannot be null.");
        this.type = Objects.requireNonNull(type, "Reconciliation type cannot be null.");
        this.status = Objects.requireNonNull(status, "Reconciliation status cannot be null.");

        this.matchSummary = matchSummary;
        this.snapshot = snapshot;

        this.createdAt = Objects.requireNonNull(createdAt, "Creation timestamp cannot be null.");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Updated timestamp cannot be null.");
        this.completedAt = completedAt;
    }

    public static Reconciliation create(
            ReconciliationType type
    ) {

        Instant now = Instant.now();

        return new Reconciliation(
                ReconciliationId.generate(),
                type,
                ReconciliationStatus.PENDING,
                null,
                null,
                now,
                now,
                null
        );
    }

    public static Reconciliation restore(
            ReconciliationId id,
            ReconciliationType type,
            ReconciliationStatus status,
            MatchSummary matchSummary,
            ReconciliationSnapshot snapshot,
            Instant createdAt,
            Instant updatedAt,
            Instant completedAt
    ) {

        return new Reconciliation(
                id,
                type,
                status,
                matchSummary,
                snapshot,
                createdAt,
                updatedAt,
                completedAt
        );
    }

    public void startProcessing() {

        ensureStatus(
                ReconciliationStatus.PENDING,
                "Only pending reconciliations can start processing."
        );

        this.status = ReconciliationStatus.PROCESSING;
        touch();
    }

    public void complete(
            MatchSummary summary,
            ReconciliationSnapshot snapshot
    ) {

        ensureStatus(
                ReconciliationStatus.PROCESSING,
                "Only processing reconciliations can be completed."
        );

        this.matchSummary = Objects.requireNonNull(summary);
        this.snapshot = Objects.requireNonNull(snapshot);

        if (summary.hasDivergences()) {
            this.status = ReconciliationStatus.DIVERGENT;
        } else {
            this.status = ReconciliationStatus.MATCHED;
        }

        this.completedAt = Instant.now();
        touch();
    }

    public void markAsPartiallyMatched(
            MatchSummary summary,
            ReconciliationSnapshot snapshot
    ) {

        ensureStatus(
                ReconciliationStatus.PROCESSING,
                "Only processing reconciliations can become partially matched."
        );

        this.matchSummary = Objects.requireNonNull(summary);
        this.snapshot = Objects.requireNonNull(snapshot);

        this.status = ReconciliationStatus.PARTIALLY_MATCHED;
        this.completedAt = Instant.now();

        touch();
    }

    public void sendToManualReview() {

        if (!status.isFinished()) {
            throw new BusinessRuleViolationException(
                    "Only completed reconciliations can be sent to manual review."
            );
        }

        this.status = ReconciliationStatus.MANUAL_REVIEW;
        touch();
    }

    public void markAsReprocessed() {

        ensureStatus(
                ReconciliationStatus.MANUAL_REVIEW,
                "Only reconciliations under manual review can be reprocessed."
        );

        this.status = ReconciliationStatus.REPROCESSED;
        touch();
    }

    public void fail() {

        this.status = ReconciliationStatus.FAILED;
        this.completedAt = Instant.now();

        touch();
    }

    public boolean canBeReprocessed() {

        return status == ReconciliationStatus.DIVERGENT
                || status == ReconciliationStatus.FAILED
                || status == ReconciliationStatus.MANUAL_REVIEW;
    }

    private void ensureStatus(
            ReconciliationStatus expected,
            String message
    ) {

        if (status != expected) {
            throw new BusinessRuleViolationException(message);
        }
    }

    private void touch() {
        this.updatedAt = Instant.now();
    }

    public ReconciliationId getId() {
        return id;
    }

    public ReconciliationType getType() {
        return type;
    }

    public ReconciliationStatus getStatus() {
        return status;
    }

    public MatchSummary getMatchSummary() {
        return matchSummary;
    }

    public ReconciliationSnapshot getSnapshot() {
        return snapshot;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

}