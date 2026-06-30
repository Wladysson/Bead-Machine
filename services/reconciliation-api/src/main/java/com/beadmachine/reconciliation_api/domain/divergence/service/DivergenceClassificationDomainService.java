package com.beadmachine.reconciliation.api.domain.divergence.service;

import com.beadmachine.reconciliation.api.domain.divergence.model.DivergenceSeverity;
import com.beadmachine.reconciliation.api.domain.divergence.model.DivergenceType;

import java.math.BigDecimal;
import java.util.Objects;

public class DivergenceClassificationDomainService {

    public DivergenceSeverity classify(
            DivergenceType type,
            BigDecimal expectedAmount,
            BigDecimal actualAmount
    ) {

        Objects.requireNonNull(type, "Divergence type cannot be null.");

        if (type.requiresManualAnalysis()) {
            return DivergenceSeverity.CRITICAL;
        }

        if (expectedAmount == null || actualAmount == null) {
            return DivergenceSeverity.HIGH;
        }

        BigDecimal difference = expectedAmount.subtract(actualAmount).abs();

        if (difference.compareTo(BigDecimal.valueOf(10000)) >= 0) {
            return DivergenceSeverity.CRITICAL;
        }

        if (difference.compareTo(BigDecimal.valueOf(1000)) >= 0) {
            return DivergenceSeverity.HIGH;
        }

        if (difference.compareTo(BigDecimal.valueOf(100)) >= 0) {
            return DivergenceSeverity.MEDIUM;
        }

        return DivergenceSeverity.LOW;
    }

}