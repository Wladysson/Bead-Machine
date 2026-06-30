package com.beadmachine.reconciliation.api.domain.divergence.model;

public enum DivergenceType {

    AMOUNT_MISMATCH,

    MISSING_INTERNAL_TRANSACTION,

    MISSING_BANK_TRANSACTION,

    DUPLICATE_TRANSACTION,

    SETTLEMENT_MISMATCH,

    CURRENCY_MISMATCH,

    DATE_MISMATCH,

    UNKNOWN;

    public boolean requiresManualAnalysis() {

        return this == DUPLICATE_TRANSACTION
                || this == SETTLEMENT_MISMATCH
                || this == UNKNOWN;
    }

}