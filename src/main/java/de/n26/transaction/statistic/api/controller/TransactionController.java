package de.n26.transaction.statistic.api.controller;

import de.n26.transaction.statistic.api.model.CreateTransactionModel;
import de.n26.transaction.statistic.api.model.TransactionStatisticModel;
import org.springframework.http.ResponseEntity;

public interface TransactionController {

    ResponseEntity<TransactionStatisticModel> findStatistic();

    ResponseEntity insertTransaction(CreateTransactionModel createTransactionModel);
}
