package com.beadmachine.reconciliation.api.domain.reconciliation.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class MatchSummary {

    private final long totalInternalTransactions;
    private final long totalBankTransactions;
    private final long matchedTransactions;
    private final long divergentTransactions;
    private final BigDecimal matchedAmount;
    private final BigDecimal divergentAmount;

    private MatchSummary(
            long totalInternalTransactions,
            long totalBankTransactions,
            long matchedTransactions,
            long divergentTransactions,
            BigDecimal matchedAmount,
            BigDecimal divergentAmount
    ) {

        if (totalInternalTransactions < 0) {
            throw new IllegalArgumentException("Total internal transactions cannot be negative.");
        }

        if (totalBankTransactions < 0) {
            throw new IllegalArgumentException("Total bank transactions cannot be negative.");
        }

        if (matchedTransactions < 0) {
            throw new IllegalArgumentException("Matched transactions cannot be negative.");
        }

        if (divergentTransactions < 0) {
            throw new IllegalArgumentException("Divergent transactions cannot be negative.");
        }

        this.totalInternalTransactions = totalInternalTransactions;
        this.totalBankTransactions = totalBankTransactions;
        this.matchedTransactions = matchedTransactions;
        this.divergentTransactions = divergentTransactions;
        this.matchedAmount = normalize(matchedAmount);
        this.divergentAmount = normalize(divergentAmount);
    }

    public static MatchSummary of(
            long totalInternalTransactions,
            long totalBankTransactions,
            long matchedTransactions,
            long divergentTransactions,
            BigDecimal matchedAmount,
            BigDecimal divergentAmount
    ) {
        return new MatchSummary(
                totalInternalTransactions,
                totalBankTransactions,
                matchedTransactions,
                divergentTransactions,
                matchedAmount,
                divergentAmount
        );
    }

    private BigDecimal normalize(BigDecimal value) {
        return Objects.requireNonNullElse(value, BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public long getTotalInternalTransactions() {
        return totalInternalTransactions;
    }

    public long getTotalBankTransactions() {
        return totalBankTransactions;
    }

    public long getMatchedTransactions() {
        return matchedTransactions;
    }

    public long getDivergentTransactions() {
        return divergentTransactions;
    }

    public BigDecimal getMatchedAmount() {
        return matchedAmount;
    }

    public BigDecimal getDivergentAmount() {
        return divergentAmount;
    }

    public BigDecimal getMatchRate() {

        if (totalInternalTransactions == 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        return BigDecimal.valueOf(matchedTransactions)
                .multiply(BigDecimal.valueOf(100))
                .divide(
                        BigDecimal.valueOf(totalInternalTransactions),
                        2,
                        RoundingMode.HALF_UP
                );
    }

    public boolean hasDivergences() {
        return divergentTransactions > 0;
    }

}