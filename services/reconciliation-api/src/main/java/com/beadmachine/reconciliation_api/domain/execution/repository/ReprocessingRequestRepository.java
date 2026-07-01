package com.beadmachine.reconciliation.api.domain.execution.repository;

import com.beadmachine.reconciliation.api.domain.common.model.PageRequest;
import com.beadmachine.reconciliation.api.domain.execution.model.ReprocessingRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReprocessingRequestRepository {

    Optional<ReprocessingRequest> findById(
            UUID requestId
    );

    List<ReprocessingRequest> findByBatchExecution(
            UUID batchExecutionId,
            PageRequest pageRequest
    );

    List<ReprocessingRequest> findPendingRequests();

    List<ReprocessingRequest> findProcessingRequests();

    ReprocessingRequest save(
            ReprocessingRequest request
    );

    boolean existsById(
            UUID requestId
    );

}