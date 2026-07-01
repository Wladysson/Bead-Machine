package com.beadmachine.reconciliation.api.domain.manual.model;

import com.beadmachine.reconciliation.api.domain.common.exception.BusinessRuleViolationException;
import com.beadmachine.reconciliation.api.domain.divergence.model.DivergenceId;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class ManualDecision {

    private final UUID id;
    private final DivergenceId divergenceId;
    private final String analyst;
    private ManualDecisionType decisionType;
    private String justification;
    private final Instant createdAt;
    private Instant updatedAt;
    private boolean applied;
    private ManualDecision(
            UUID id,
            DivergenceId divergenceId,
            String analyst,
            ManualDecisionType decisionType,
            String justification,
            Instant createdAt,
            Instant updatedAt,
            boolean applied
    ) {

        this.id = Objects.requireNonNull(id);
        this.divergenceId = Objects.requireNonNull(divergenceId);
        this.analyst = Objects.requireNonNull(analyst);
        this.decisionType = Objects.requireNonNull(decisionType);
        this.justification = Objects.requireNonNull(justification);

        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);

        this.applied = applied;
    }

    public static ManualDecision create(
            ManualReconciliationCommand command
    ) {

        Instant now = Instant.now();

        return new ManualDecision(
                UUID.randomUUID(),
                command.getDivergenceId(),
                command.getAnalyst(),
                command.getDecisionType(),
                command.getJustification(),
                now,
                now,
                false
        );
    }

    public void apply() {

        if (applied) {
            throw new BusinessRuleViolationException(
                    "Manual decision has already been applied."
            );
        }

        applied = true;
        updatedAt = Instant.now();
    }

    public void changeDecision(
            ManualDecisionType decisionType,
            String justification
    ) {

        if (applied) {
            throw new BusinessRuleViolationException(
                    "Applied manual decisions cannot be modified."
            );
        }

        this.decisionType = Objects.requireNonNull(decisionType);
        this.justification = Objects.requireNonNull(justification);

        this.updatedAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public DivergenceId getDivergenceId() {
        return divergenceId;
    }

    public String getAnalyst() {
        return analyst;
    }

    public ManualDecisionType getDecisionType() {
        return decisionType;
    }

    public String getJustification() {
        return justification;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public boolean isApplied() {
        return applied;
    }

}