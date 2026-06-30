package com.beadmachine.reconciliation.api.domain.execution.model;

import com.beadmachine.reconciliation.api.domain.common.exception.BusinessRuleViolationException;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class BatchExecution {

    private final UUID id;

    private final String jobName;

    private final Instant startedAt;

    private Instant finishedAt;

    private JobExecutionStatus status;

    private JobExecutionSummary summary;

    public BatchExecution(
            UUID id,
            String jobName,
            Instant startedAt,
            JobExecutionStatus status,
            JobExecutionSummary summary
    ) {

        this.id = Objects.requireNonNull(id);
        this.jobName = Objects.requireNonNull(jobName);
        this.startedAt = Objects.requireNonNull(startedAt);
        this.status = Objects.requireNonNull(status);
        this.summary = summary;
    }

    public static BatchExecution create(String jobName) {

        return new BatchExecution(
                UUID.randomUUID(),
                jobName,
                Instant.now(),
                JobExecutionStatus.CREATED,
                null
        );
    }

    public void start() {

        if (status != JobExecutionStatus.CREATED &&
                status != JobExecutionStatus.WAITING) {

            throw new BusinessRuleViolationException(
                    "Batch execution cannot be started."
            );
        }

        this.status = JobExecutionStatus.RUNNING;
    }

    public void complete(JobExecutionSummary summary) {

        if (!status.isRunning()) {
            throw new BusinessRuleViolationException(
                    "Only running jobs can be completed."
            );
        }

        this.summary = Objects.requireNonNull(summary);

        this.finishedAt = Instant.now();

        this.status = JobExecutionStatus.COMPLETED;
    }

    public void fail(JobExecutionSummary summary) {

        this.summary = summary;

        this.finishedAt = Instant.now();

        this.status = JobExecutionStatus.FAILED;
    }

    public void cancel() {

        if (!status.canBeCancelled()) {
            throw new BusinessRuleViolationException(
                    "Execution cannot be cancelled."
            );
        }

        this.finishedAt = Instant.now();

        this.status = JobExecutionStatus.CANCELLED;
    }

    public Duration executionTime() {

        Instant end = finishedAt == null
                ? Instant.now()
                : finishedAt;

        return Duration.between(startedAt, end);
    }

    public UUID getId() {
        return id;
    }

    public String getJobName() {
        return jobName;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }

    public JobExecutionStatus getStatus() {
        return status;
    }

    public JobExecutionSummary getSummary() {
        return summary;
    }

}