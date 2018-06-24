package de.n26.transaction.statistic.api.service;

import de.n26.transaction.statistic.api.model.CreateTransactionModel;
import de.n26.transaction.statistic.api.model.TransactionStatisticModel;

public interface TransactionService {

    TransactionStatisticModel getStatistics();

    boolean insertTransaction(CreateTransactionModel createTransactionModel);
}
