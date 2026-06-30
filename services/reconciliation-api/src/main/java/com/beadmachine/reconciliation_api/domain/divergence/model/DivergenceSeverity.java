package com.beadmachine.reconciliation.api.domain.divergence.model;

public enum DivergenceSeverity {

    LOW,

    MEDIUM,

    HIGH,

    CRITICAL;

    public boolean isCritical() {
        return this == CRITICAL;
    }

    public boolean requiresImmediateAction() {

        return this == HIGH
                || this == CRITICAL;
    }

}