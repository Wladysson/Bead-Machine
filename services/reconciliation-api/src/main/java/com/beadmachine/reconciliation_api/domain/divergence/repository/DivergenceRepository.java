package com.beadmachine.reconciliation.api.domain.divergence.repository;

import com.beadmachine.reconciliation.api.domain.common.model.DateRange;
import com.beadmachine.reconciliation.api.domain.common.model.PageRequest;
import com.beadmachine.reconciliation.api.domain.divergence.model.Divergence;
import com.beadmachine.reconciliation.api.domain.divergence.model.DivergenceId;
import com.beadmachine.reconciliation.api.domain.divergence.model.DivergenceSeverity;
import com.beadmachine.reconciliation.api.domain.divergence.model.DivergenceStatus;
import com.beadmachine.reconciliation.api.domain.divergence.model.DivergenceType;

import java.util.List;
import java.util.Optional;

public interface DivergenceRepository {

    Optional<Divergence> findById(DivergenceId id);

    List<Divergence> search(
            PageRequest pageRequest,
            DateRange dateRange,
            DivergenceStatus status,
            DivergenceSeverity severity,
            DivergenceType type
    );

    long count(
            DateRange dateRange,
            DivergenceStatus status,
            DivergenceSeverity severity,
            DivergenceType type
    );

    List<Divergence> findOpenDivergences();

    Divergence save(Divergence divergence);

    boolean existsById(DivergenceId id);

}