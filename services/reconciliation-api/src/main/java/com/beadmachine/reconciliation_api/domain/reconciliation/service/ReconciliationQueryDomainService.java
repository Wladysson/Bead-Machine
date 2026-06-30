package com.beadmachine.reconciliation.api.domain.reconciliation.service;

import com.beadmachine.reconciliation.api.domain.common.exception.ResourceNotFoundException;
import com.beadmachine.reconciliation.api.domain.common.model.DateRange;
import com.beadmachine.reconciliation.api.domain.common.model.PageRequest;
import com.beadmachine.reconciliation.api.domain.reconciliation.model.Reconciliation;
import com.beadmachine.reconciliation.api.domain.reconciliation.model.ReconciliationId;
import com.beadmachine.reconciliation.api.domain.reconciliation.model.ReconciliationStatus;
import com.beadmachine.reconciliation.api.domain.reconciliation.model.ReconciliationType;
import com.beadmachine.reconciliation.api.domain.reconciliation.repository.ReconciliationRepository;

import java.util.List;
import java.util.Objects;

public class ReconciliationQueryDomainService {

    private final ReconciliationRepository repository;

    public ReconciliationQueryDomainService(
            ReconciliationRepository repository
    ) {
        this.repository = Objects.requireNonNull(repository);
    }

    public Reconciliation findById(
            ReconciliationId reconciliationId
    ) {

        return repository.findById(reconciliationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Reconciliation",
                                reconciliationId.value()
                        )
                );
    }

    public List<Reconciliation> search(
            PageRequest pageRequest,
            DateRange dateRange,
            ReconciliationStatus status,
            ReconciliationType type
    ) {

        return repository.search(
                pageRequest,
                dateRange,
                status,
                type
        );
    }

    public long total(
            DateRange dateRange,
            ReconciliationStatus status,
            ReconciliationType type
    ) {

        return repository.count(
                dateRange,
                status,
                type
        );
    }

}