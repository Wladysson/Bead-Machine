package com.beadmachine.reconciliation.api.domain.execution.service;

import com.beadmachine.reconciliation.api.domain.common.exception.ResourceNotFoundException;
import com.beadmachine.reconciliation.api.domain.common.model.DateRange;
import com.beadmachine.reconciliation.api.domain.common.model.PageRequest;
import com.beadmachine.reconciliation.api.domain.execution.model.BatchExecution;
import com.beadmachine.reconciliation.api.domain.execution.model.JobExecutionStatus;
import com.beadmachine.reconciliation.api.domain.execution.repository.JobExecutionRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ExecutionQueryDomainService {

    private final JobExecutionRepository repository;

    public ExecutionQueryDomainService(
            JobExecutionRepository repository
    ) {
        this.repository = Objects.requireNonNull(repository);
    }

    public BatchExecution findById(UUID executionId) {

        return repository.findById(executionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "BatchExecution",
                                executionId
                        )
                );
    }

    public List<BatchExecution> search(
            PageRequest pageRequest,
            DateRange executionPeriod,
            JobExecutionStatus status
    ) {

        return repository.search(
                pageRequest,
                executionPeriod,
                status
        );
    }

    public List<BatchExecution> findRunningExecutions() {
        return repository.findRunningExecutions();
    }

    public BatchExecution findLatestExecution(
            String jobName
    ) {

        return repository.findLatestExecution(jobName)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Latest execution not found for job",
                                jobName
                        )
                );
    }

    public long total(
            DateRange executionPeriod,
            JobExecutionStatus status
    ) {

        return repository.count(
                executionPeriod,
                status
        );
    }

}