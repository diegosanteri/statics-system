package de.n26.transaction.statistic.controller;

import de.n26.transaction.statistic.model.CreateTransactionModel;
import de.n26.transaction.statistic.model.TransactionStatisticModel;
import org.springframework.http.ResponseEntity;

public interface TransactionController {

    ResponseEntity<TransactionStatisticModel> findStatistic();

    ResponseEntity insertTransaction(CreateTransactionModel createTransactionModel);
}
