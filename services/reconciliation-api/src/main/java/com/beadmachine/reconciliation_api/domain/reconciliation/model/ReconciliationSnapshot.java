package com.beadmachine.reconciliation.api.domain.reconciliation.model;

import java.time.Instant;
import java.util.Objects;

public final class ReconciliationSnapshot {

    private final Instant generatedAt;
    private final String sourceFileName;
    private final String batchIdentifier;
    private final String checksum;
    private final long processedRecords;

    private ReconciliationSnapshot(
            Instant generatedAt,
            String sourceFileName,
            String batchIdentifier,
            String checksum,
            long processedRecords
    ) {

        this.generatedAt = Objects.requireNonNull(
                generatedAt,
                "Generated timestamp cannot be null."
        );

        this.sourceFileName = Objects.requireNonNull(
                sourceFileName,
                "Source file name cannot be null."
        );

        this.batchIdentifier = Objects.requireNonNull(
                batchIdentifier,
                "Batch identifier cannot be null."
        );

        this.checksum = Objects.requireNonNull(
                checksum,
                "Checksum cannot be null."
        );

        if (processedRecords < 0) {
            throw new IllegalArgumentException(
                    "Processed records cannot be negative."
            );
        }

        this.processedRecords = processedRecords;
    }

    public static ReconciliationSnapshot of(
            Instant generatedAt,
            String sourceFileName,
            String batchIdentifier,
            String checksum,
            long processedRecords
    ) {

        return new ReconciliationSnapshot(
                generatedAt,
                sourceFileName,
                batchIdentifier,
                checksum,
                processedRecords
        );
    }

    public Instant getGeneratedAt() {
        return generatedAt;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public String getBatchIdentifier() {
        return batchIdentifier;
    }

    public String getChecksum() {
        return checksum;
    }

    public long getProcessedRecords() {
        return processedRecords;
    }

}