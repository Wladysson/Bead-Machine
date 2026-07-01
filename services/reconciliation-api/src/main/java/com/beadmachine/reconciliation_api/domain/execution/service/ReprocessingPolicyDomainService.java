package com.beadmachine.reconciliation.api.domain.execution.service;

import com.beadmachine.reconciliation.api.domain.common.exception.BusinessRuleViolationException;
import com.beadmachine.reconciliation.api.domain.execution.model.BatchExecution;
import com.beadmachine.reconciliation.api.domain.execution.model.JobExecutionStatus;
import com.beadmachine.reconciliation.api.domain.execution.model.ReprocessingRequest;
import com.beadmachine.reconciliation.api.domain.execution.repository.ReprocessingRequestRepository;

import java.util.List;
import java.util.Objects;

public class ReprocessingPolicyDomainService {

    private final ReprocessingRequestRepository repository;

    public ReprocessingPolicyDomainService(
            ReprocessingRequestRepository repository
    ) {
        this.repository = Objects.requireNonNull(repository);
    }

    public void validateNewRequest(
            BatchExecution execution
    ) {

        Objects.requireNonNull(execution);

        if (execution.getStatus() == JobExecutionStatus.RUNNING) {
            throw new BusinessRuleViolationException(
                    "Running executions cannot be reprocessed."
            );
        }

        if (execution.getStatus() == JobExecutionStatus.CREATED
                || execution.getStatus() == JobExecutionStatus.WAITING) {

            throw new BusinessRuleViolationException(
                    "Execution has not finished yet."
            );
        }
    }

    public void validateNoPendingRequest() {

        List<ReprocessingRequest> pending =
                repository.findPendingRequests();

        if (!pending.isEmpty()) {

            throw new BusinessRuleViolationException(
                    "There are pending reprocessing requests awaiting execution."
            );
        }
    }

    public boolean canBeReprocessed(
            BatchExecution execution
    ) {

        return execution.getStatus() == JobExecutionStatus.FAILED
                || execution.getStatus() == JobExecutionStatus.COMPLETED
                || execution.getStatus() == JobExecutionStatus.CANCELLED;
    }

}