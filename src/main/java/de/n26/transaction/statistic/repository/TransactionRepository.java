package de.n26.transaction.statistic.repository;

import de.n26.transaction.statistic.model.TransactionStatisticModel;

public interface TransactionRepository {

    void updateSummaryTransaction(TransactionStatisticModel transactionStatisticModel);

    TransactionStatisticModel findStatistics();
}