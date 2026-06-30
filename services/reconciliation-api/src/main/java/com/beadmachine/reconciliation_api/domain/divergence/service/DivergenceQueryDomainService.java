package com.beadmachine.reconciliation.api.domain.divergence.service;

import com.beadmachine.reconciliation.api.domain.common.exception.ResourceNotFoundException;
import com.beadmachine.reconciliation.api.domain.common.model.DateRange;
import com.beadmachine.reconciliation.api.domain.common.model.PageRequest;
import com.beadmachine.reconciliation.api.domain.divergence.model.Divergence;
import com.beadmachine.reconciliation.api.domain.divergence.model.DivergenceId;
import com.beadmachine.reconciliation.api.domain.divergence.model.DivergenceSeverity;
import com.beadmachine.reconciliation.api.domain.divergence.model.DivergenceStatus;
import com.beadmachine.reconciliation.api.domain.divergence.model.DivergenceType;
import com.beadmachine.reconciliation.api.domain.divergence.repository.DivergenceRepository;

import java.util.List;
import java.util.Objects;

public class DivergenceQueryDomainService {

    private final DivergenceRepository repository;

    public DivergenceQueryDomainService(
            DivergenceRepository repository
    ) {
        this.repository = Objects.requireNonNull(repository);
    }

    public Divergence findById(DivergenceId id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Divergence",
                                id.value()
                        ));
    }

    public List<Divergence> search(
            PageRequest pageRequest,
            DateRange dateRange,
            DivergenceStatus status,
            DivergenceSeverity severity,
            DivergenceType type
    ) {

        return repository.search(
                pageRequest,
                dateRange,
                status,
                severity,
                type
        );
    }

    public List<Divergence> findOpenDivergences() {
        return repository.findOpenDivergences();
    }

    public long total(
            DateRange dateRange,
            DivergenceStatus status,
            DivergenceSeverity severity,
            DivergenceType type
    ) {

        return repository.count(
                dateRange,
                status,
                severity,
                type
        );
    }

}