package com.beadmachine.reconciliation.api.domain.divergence.model;

import com.beadmachine.reconciliation.api.domain.common.exception.BusinessRuleViolationException;

import java.time.Instant;
import java.util.Objects;

public class Divergence {

    private final DivergenceId id;
    private final DivergenceType type;
    private final DivergenceSeverity severity;

    private DivergenceStatus status;
    private DivergenceDetail detail;

    private String assignedTo;

    private final Instant createdAt;
    private Instant updatedAt;
    private Instant resolvedAt;

    private Divergence(
            DivergenceId id,
            DivergenceType type,
            DivergenceSeverity severity,
            DivergenceStatus status,
            DivergenceDetail detail,
            String assignedTo,
            Instant createdAt,
            Instant updatedAt,
            Instant resolvedAt
    ) {

        this.id = Objects.requireNonNull(id);
        this.type = Objects.requireNonNull(type);
        this.severity = Objects.requireNonNull(severity);
        this.status = Objects.requireNonNull(status);
        this.detail = Objects.requireNonNull(detail);

        this.assignedTo = assignedTo;

        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
        this.resolvedAt = resolvedAt;
    }

    public static Divergence create(
            DivergenceType type,
            DivergenceSeverity severity,
            DivergenceDetail detail
    ) {

        Instant now = Instant.now();

        return new Divergence(
                DivergenceId.generate(),
                type,
                severity,
                DivergenceStatus.OPEN,
                detail,
                null,
                now,
                now,
                null
        );
    }

    public void assign(String analyst) {

        if (!status.canBeAssigned()) {
            throw new BusinessRuleViolationException(
                    "Only open divergences can be assigned."
            );
        }

        this.assignedTo = Objects.requireNonNull(analyst);

        this.status = DivergenceStatus.ASSIGNED;

        touch();
    }

    public void startAnalysis() {

        if (status != DivergenceStatus.ASSIGNED) {
            throw new BusinessRuleViolationException(
                    "Only assigned divergences can enter analysis."
            );
        }

        this.status = DivergenceStatus.IN_ANALYSIS;

        touch();
    }

    public void resolve() {

        if (!status.isOpen()) {
            throw new BusinessRuleViolationException(
                    "Only active divergences can be resolved."
            );
        }

        this.status = DivergenceStatus.RESOLVED;
        this.resolvedAt = Instant.now();

        touch();
    }

    public void reject() {

        if (!status.isOpen()) {
            throw new BusinessRuleViolationException(
                    "Only active divergences can be rejected."
            );
        }

        this.status = DivergenceStatus.REJECTED;
        this.resolvedAt = Instant.now();

        touch();
    }

    public void close() {

        if (!status.isClosed()) {
            throw new BusinessRuleViolationException(
                    "Only finalized divergences can be closed."
            );
        }

        this.status = DivergenceStatus.CLOSED;

        touch();
    }

    private void touch() {
        this.updatedAt = Instant.now();
    }

    public DivergenceId getId() {
        return id;
    }

    public DivergenceType getType() {
        return type;
    }

    public DivergenceSeverity getSeverity() {
        return severity;
    }

    public DivergenceStatus getStatus() {
        return status;
    }

    public DivergenceDetail getDetail() {
        return detail;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getResolvedAt() {
        return resolvedAt;
    }

    public boolean isCritical() {
        return severity.isCritical();
    }

    public boolean requiresImmediateAction() {
        return severity.requiresImmediateAction();
    }

}