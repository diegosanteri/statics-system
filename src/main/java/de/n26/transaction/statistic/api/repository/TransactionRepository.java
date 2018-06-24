package de.n26.transaction.statistic.api.repository;

import de.n26.transaction.statistic.api.model.TransactionStatisticModel;

public interface TransactionRepository {

    void updateSummaryTransaction(TransactionStatisticModel transactionStatisticModel);

    TransactionStatisticModel findStatistics();
}