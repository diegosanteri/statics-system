package de.n26.transaction.statistic.api.service;

import de.n26.transaction.statistic.api.model.CreateTransactionModel;
import de.n26.transaction.statistic.api.model.TransactionStatisticModel;
import de.n26.transaction.statistic.api.util.TimestampUtils;

public abstract class TransactionQueueService implements Runnable{

    public abstract boolean notifyTransaction(CreateTransactionModel createTransactionModel);
    protected abstract void checkUnprocessedQueue(TransactionStatisticModel summary);
    protected abstract void checkProcessedQueue(long minimumTimestamp, TransactionStatisticModel summary);
    protected abstract TransactionStatisticModel findCurrentSummary();
    protected abstract void saveTransaction(TransactionStatisticModel summary);

    @Override
    public void run() {

        while(true) {

            long minimumTimestamp = TimestampUtils.decreaseDate(System.currentTimeMillis(), 1);
            TransactionStatisticModel summary = findCurrentSummary();

            checkUnprocessedQueue(summary);
            checkProcessedQueue(minimumTimestamp, summary);
            saveTransaction(summary);
        }
    }
}
