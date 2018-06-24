package de.n26.transaction.statistic.api.controller;

import de.n26.transaction.statistic.api.model.CreateTransactionModel;
import de.n26.transaction.statistic.api.model.TransactionStatisticModel;
import de.n26.transaction.statistic.api.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TransactionControllerImpl {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/statistics")
    public ResponseEntity<TransactionStatisticModel> findStatistic() {

        return ResponseEntity.ok(transactionService.getStatistics());
    }

    @PostMapping(value = "/transactions")
    public ResponseEntity insertTransaction(@Valid @RequestBody final CreateTransactionModel createTransactionModel) {

        if(transactionService.insertTransaction(createTransactionModel)) {

            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.noContent().build();
    }
}
