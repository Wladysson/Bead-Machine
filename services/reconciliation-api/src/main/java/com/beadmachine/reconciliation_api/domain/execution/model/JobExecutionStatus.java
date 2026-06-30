package com.beadmachine.reconciliation.api.domain.execution.model;

public enum JobExecutionStatus {

    CREATED,

    WAITING,

    RUNNING,

    COMPLETED,

    FAILED,

    CANCELLED;

    public boolean isRunning() {

        return this == CREATED
                || this == WAITING
                || this == RUNNING;
    }

    public boolean isFinished() {

        return this == COMPLETED
                || this == FAILED
                || this == CANCELLED;
    }

    public boolean canBeCancelled() {
        return isRunning();
    }

    public boolean succeeded() {
        return this == COMPLETED;
    }

}