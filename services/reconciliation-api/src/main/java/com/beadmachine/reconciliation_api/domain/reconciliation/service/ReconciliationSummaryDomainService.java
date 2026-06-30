package com.beadmachine.reconciliation.api.domain.reconciliation.service;

import com.beadmachine.reconciliation.api.domain.reconciliation.model.MatchSummary;
import com.beadmachine.reconciliation.api.domain.reconciliation.model.Reconciliation;

import java.math.BigDecimal;
import java.util.Collection;

public class ReconciliationSummaryDomainService {

    public MatchSummary consolidate(
            Collection<Reconciliation> reconciliations
    ) {

        long internalTransactions = 0;
        long bankTransactions = 0;
        long matchedTransactions = 0;
        long divergentTransactions = 0;

        BigDecimal matchedAmount = BigDecimal.ZERO;
        BigDecimal divergentAmount = BigDecimal.ZERO;

        for (Reconciliation reconciliation : reconciliations) {

            MatchSummary summary = reconciliation.getMatchSummary();

            if (summary == null) {
                continue;
            }

            internalTransactions += summary.getTotalInternalTransactions();
            bankTransactions += summary.getTotalBankTransactions();
            matchedTransactions += summary.getMatchedTransactions();
            divergentTransactions += summary.getDivergentTransactions();

            matchedAmount = matchedAmount.add(
                    summary.getMatchedAmount()
            );

            divergentAmount = divergentAmount.add(
                    summary.getDivergentAmount()
            );
        }

        return MatchSummary.of(
                internalTransactions,
                bankTransactions,
                matchedTransactions,
                divergentTransactions,
                matchedAmount,
                divergentAmount
        );
    }

}