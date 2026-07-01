package com.beadmachine.reconciliation.api.domain.execution.repository;

import com.beadmachine.reconciliation.api.domain.common.model.DateRange;
import com.beadmachine.reconciliation.api.domain.common.model.PageRequest;
import com.beadmachine.reconciliation.api.domain.execution.model.BatchExecution;
import com.beadmachine.reconciliation.api.domain.execution.model.JobExecutionStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobExecutionRepository {

    Optional<BatchExecution> findById(UUID executionId);

    List<BatchExecution> search(
            PageRequest pageRequest,
            DateRange executionPeriod,
            JobExecutionStatus status
    );

    long count(
            DateRange executionPeriod,
            JobExecutionStatus status
    );

    Optional<BatchExecution> findLatestExecution(
            String jobName
    );

    List<BatchExecution> findRunningExecutions();

    BatchExecution save(
            BatchExecution execution
    );

    boolean existsById(
            UUID executionId
    );

}