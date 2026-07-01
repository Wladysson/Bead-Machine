package com.beadmachine.reconciliation.api.domain.execution.model;

import com.beadmachine.reconciliation.api.domain.common.exception.BusinessRuleViolationException;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class ReprocessingRequest {

    public enum Status {
        REQUESTED,
        ACCEPTED,
        PROCESSING,
        COMPLETED,
        FAILED,
        CANCELLED
    }

    private final UUID id;
    private final UUID batchExecutionId;
    private final String requestedBy;
    private final String reason;
    private final Instant requestedAt;
    private Status status;
    private Instant processedAt;
    private String processingNotes;
    private ReprocessingRequest(
            UUID id,
            UUID batchExecutionId,
            String requestedBy,
            String reason,
            Instant requestedAt,
            Status status
    ) {

        this.id = Objects.requireNonNull(id);
        this.batchExecutionId = Objects.requireNonNull(batchExecutionId);
        this.requestedBy = Objects.requireNonNull(requestedBy);
        this.reason = Objects.requireNonNull(reason);
        this.requestedAt = Objects.requireNonNull(requestedAt);
        this.status = Objects.requireNonNull(status);
    }

    public static ReprocessingRequest create(
            UUID batchExecutionId,
            String requestedBy,
            String reason
    ) {

        return new ReprocessingRequest(
                UUID.randomUUID(),
                batchExecutionId,
                requestedBy,
                reason,
                Instant.now(),
                Status.REQUESTED
        );
    }

    public void accept() {

        if (status != Status.REQUESTED) {
            throw new BusinessRuleViolationException(
                    "Only requested reprocessing can be accepted."
            );
        }

        status = Status.ACCEPTED;
    }

    public void start() {

        if (status != Status.ACCEPTED) {
            throw new BusinessRuleViolationException(
                    "Only accepted requests can start processing."
            );
        }

        status = Status.PROCESSING;
    }

    public void complete(String notes) {

        if (status != Status.PROCESSING) {
            throw new BusinessRuleViolationException(
                    "Only processing requests can be completed."
            );
        }

        this.processingNotes = notes;
        this.processedAt = Instant.now();
        this.status = Status.COMPLETED;
    }

    public void fail(String notes) {

        if (status != Status.PROCESSING) {
            throw new BusinessRuleViolationException(
                    "Only processing requests can fail."
            );
        }

        this.processingNotes = notes;
        this.processedAt = Instant.now();
        this.status = Status.FAILED;
    }

    public void cancel() {

        if (status == Status.COMPLETED ||
                status == Status.FAILED) {

            throw new BusinessRuleViolationException(
                    "Finished requests cannot be cancelled."
            );
        }

        this.status = Status.CANCELLED;
        this.processedAt = Instant.now();
    }

    public boolean isFinished() {

        return status == Status.COMPLETED
                || status == Status.FAILED
                || status == Status.CANCELLED;
    }

    public UUID getId() {
        return id;
    }

    public UUID getBatchExecutionId() {
        return batchExecutionId;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public String getReason() {
        return reason;
    }

    public Instant getRequestedAt() {
        return requestedAt;
    }

    public Status getStatus() {
        return status;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }

    public String getProcessingNotes() {
        return processingNotes;
    }

}