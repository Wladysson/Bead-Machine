package com.beadmachine.reconciliation.api.domain.divergence.model;

import java.time.Instant;
import java.util.Objects;

public final class DivergenceDetail {

    private final String internalTransactionId;
    private final String bankTransactionId;
    private final String description;
    private final String detectedRule;
    private final Instant detectedAt;

    private DivergenceDetail(
            String internalTransactionId,
            String bankTransactionId,
            String description,
            String detectedRule,
            Instant detectedAt
    ) {

        this.internalTransactionId = internalTransactionId;
        this.bankTransactionId = bankTransactionId;

        this.description = Objects.requireNonNull(
                description,
                "Description cannot be null."
        );

        this.detectedRule = Objects.requireNonNull(
                detectedRule,
                "Detected rule cannot be null."
        );

        this.detectedAt = Objects.requireNonNull(
                detectedAt,
                "Detection timestamp cannot be null."
        );
    }

    public static DivergenceDetail of(
            String internalTransactionId,
            String bankTransactionId,
            String description,
            String detectedRule,
            Instant detectedAt
    ) {

        return new DivergenceDetail(
                internalTransactionId,
                bankTransactionId,
                description,
                detectedRule,
                detectedAt
        );
    }

    public String getInternalTransactionId() {
        return internalTransactionId;
    }

    public String getBankTransactionId() {
        return bankTransactionId;
    }

    public String getDescription() {
        return description;
    }

    public String getDetectedRule() {
        return detectedRule;
    }

    public Instant getDetectedAt() {
        return detectedAt;
    }

}