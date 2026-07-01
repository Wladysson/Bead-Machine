package com.beadmachine.reconciliation.api.domain.manual.model;

public enum ManualDecisionType {

    APPROVE,

    REJECT,

    FORCE_MATCH,

    IGNORE,

    ESCALATE;

    public boolean finishesWorkflow() {

        return this == APPROVE
                || this == REJECT
                || this == FORCE_MATCH
                || this == IGNORE;
    }

    public boolean requiresEscalation() {
        return this == ESCALATE;
    }

}