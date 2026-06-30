package com.beadmachine.reconciliation.api.domain.execution.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class JobExecutionSummary {

    private final long totalRecords;
    private final long processedRecords;
    private final long successfulRecords;
    private final long failedRecords;
    private final long skippedRecords;
    private final BigDecimal reconciledAmount;

    private JobExecutionSummary(
            long totalRecords,
            long processedRecords,
            long successfulRecords,
            long failedRecords,
            long skippedRecords,
            BigDecimal reconciledAmount
    ) {

        if (totalRecords < 0) {
            throw new IllegalArgumentException("Total records cannot be negative.");
        }

        if (processedRecords < 0) {
            throw new IllegalArgumentException("Processed records cannot be negative.");
        }

        this.totalRecords = totalRecords;
        this.processedRecords = processedRecords;
        this.successfulRecords = successfulRecords;
        this.failedRecords = failedRecords;
        this.skippedRecords = skippedRecords;
        this.reconciledAmount = Objects.requireNonNull(reconciledAmount);
    }

    public static JobExecutionSummary of(
            long totalRecords,
            long processedRecords,
            long successfulRecords,
            long failedRecords,
            long skippedRecords,
            BigDecimal reconciledAmount
    ) {

        return new JobExecutionSummary(
                totalRecords,
                processedRecords,
                successfulRecords,
                failedRecords,
                skippedRecords,
                reconciledAmount
        );
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public long getProcessedRecords() {
        return processedRecords;
    }

    public long getSuccessfulRecords() {
        return successfulRecords;
    }

    public long getFailedRecords() {
        return failedRecords;
    }

    public long getSkippedRecords() {
        return skippedRecords;
    }

    public BigDecimal getReconciledAmount() {
        return reconciledAmount;
    }

    public double successRate() {

        if (processedRecords == 0) {
            return 0D;
        }

        return (successfulRecords * 100D) / processedRecords;
    }

    public boolean hasFailures() {
        return failedRecords > 0;
    }

}