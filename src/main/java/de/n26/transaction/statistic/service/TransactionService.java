package de.n26.transaction.statistic.service;

import de.n26.transaction.statistic.model.CreateTransactionModel;
import de.n26.transaction.statistic.model.TransactionStatisticModel;

public interface TransactionService {

    TransactionStatisticModel getStatistics();

    boolean insertTransaction(CreateTransactionModel createTransactionModel);
}
