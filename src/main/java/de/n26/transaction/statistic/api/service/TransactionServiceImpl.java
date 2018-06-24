package de.n26.transaction.statistic.api.service;

import de.n26.transaction.statistic.api.model.TransactionStatisticModel;
import de.n26.transaction.statistic.api.model.CreateTransactionModel;
import de.n26.transaction.statistic.api.repository.TransactionRepository;
import de.n26.transaction.statistic.api.util.TimestampUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionQueueService transactionQueueService;

    @Autowired
    public TransactionServiceImpl (
            final TransactionRepository transactionRepository,
            final TransactionQueueService transactionQueueService) {

        this.transactionRepository = transactionRepository;
        this.transactionQueueService = transactionQueueService;
    }

    @Override
    public TransactionStatisticModel getStatistics() {

        return transactionRepository.findStatistics();
    }

    @Override
    public boolean insertTransaction(final CreateTransactionModel createTransactionModel) {

        long minimumTimestamp = TimestampUtils.decreaseDate(System.currentTimeMillis(), 1);
        if(createTransactionModel.getTimestamp() < minimumTimestamp) {

            return false;
        }

        transactionQueueService.notifyTransaction(createTransactionModel);

        return true;
    }
}
