package com.beadmachine.reconciliation.api.domain.reconciliation.repository;

import com.beadmachine.reconciliation.api.domain.common.model.DateRange;
import com.beadmachine.reconciliation.api.domain.common.model.PageRequest;
import com.beadmachine.reconciliation.api.domain.reconciliation.model.Reconciliation;
import com.beadmachine.reconciliation.api.domain.reconciliation.model.ReconciliationId;
import com.beadmachine.reconciliation.api.domain.reconciliation.model.ReconciliationStatus;
import com.beadmachine.reconciliation.api.domain.reconciliation.model.ReconciliationType;

import java.util.List;
import java.util.Optional;

public interface ReconciliationRepository {

    Optional<Reconciliation> findById(ReconciliationId id);

    List<Reconciliation> search(
            PageRequest pageRequest,
            DateRange dateRange,
            ReconciliationStatus status,
            ReconciliationType type
    );

    long count(
            DateRange dateRange,
            ReconciliationStatus status,
            ReconciliationType type
    );

    Reconciliation save(Reconciliation reconciliation);

    boolean existsById(ReconciliationId id);

}