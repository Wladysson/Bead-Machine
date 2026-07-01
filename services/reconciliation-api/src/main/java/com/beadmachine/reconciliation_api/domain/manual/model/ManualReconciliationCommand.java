package com.beadmachine.reconciliation.api.domain.manual.model;

import com.beadmachine.reconciliation.api.domain.divergence.model.DivergenceId;

import java.time.Instant;
import java.util.Objects;

public final class ManualReconciliationCommand {
    private final DivergenceId divergenceId;
    private final ManualDecisionType decisionType;
    private final String analyst;
    private final String justification;
    private final Instant requestedAt;
    private ManualReconciliationCommand(
            DivergenceId divergenceId,
            ManualDecisionType decisionType,
            String analyst,
            String justification,
            Instant requestedAt
    ) {

        this.divergenceId = Objects.requireNonNull(divergenceId);
        this.decisionType = Objects.requireNonNull(decisionType);

        this.analyst = Objects.requireNonNull(analyst);

        this.justification = Objects.requireNonNull(justification);

        this.requestedAt = Objects.requireNonNull(requestedAt);
    }

    public static ManualReconciliationCommand of(
            DivergenceId divergenceId,
            ManualDecisionType decisionType,
            String analyst,
            String justification
    ) {

        return new ManualReconciliationCommand(
                divergenceId,
                decisionType,
                analyst,
                justification,
                Instant.now()
        );
    }

    public DivergenceId getDivergenceId() {
        return divergenceId;
    }

    public ManualDecisionType getDecisionType() {
        return decisionType;
    }

    public String getAnalyst() {
        return analyst;
    }

    public String getJustification() {
        return justification;
    }

    public Instant getRequestedAt() {
        return requestedAt;
    }

}