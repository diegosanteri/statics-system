package de.n26.transaction.statistic.service;

import de.n26.transaction.statistic.model.CreateTransactionModel;
import de.n26.transaction.statistic.model.TransactionStatisticModel;
import de.n26.transaction.statistic.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TransactionQueueServiceImpl extends TransactionQueueService {

    private final TransactionRepository transactionRepository;
    protected final static Queue<CreateTransactionModel> unprocessedQueue =
            new ConcurrentLinkedQueue<>();
    protected final static List<CreateTransactionModel> processedQueue =
            Collections.synchronizedList(new ArrayList<CreateTransactionModel>());

    @Autowired
    public TransactionQueueServiceImpl(final TransactionRepository transactionRepository) {

        this.transactionRepository = transactionRepository;

        final Thread transactionProccessThread = new Thread(this);
        transactionProccessThread.start();
    }

    @Override
    public boolean notifyTransaction(final CreateTransactionModel createTransactionModel) {

        return unprocessedQueue.add(createTransactionModel);
    }

    @Override
    protected TransactionStatisticModel findCurrentSummary() {

        return transactionRepository.findStatistics();
    }

    @Override
    protected void saveTransaction(final TransactionStatisticModel summary) {

        transactionRepository.updateSummaryTransaction(summary);
    }

    @Override
    protected void checkUnprocessedQueue(final TransactionStatisticModel summary) {

        CreateTransactionModel transaction;
        while((transaction = unprocessedQueue.poll()) != null) {

            long count = summary.getCount() + 1;
            double amount = summary.getSum() + transaction.getAmount();

            if(summary.getCount() == 0) {

                summary.setCount(count);
                summary.setSum(transaction.getAmount());
                summary.setMax(transaction.getAmount());
                summary.setMin(transaction.getAmount());
                summary.setAvg(transaction.getAmount());
            } else {

                summary.setCount(count);
                summary.setSum(amount);
                summary.setMax(Math.max(transaction.getAmount(), summary.getMax()));
                summary.setMin(Math.min(transaction.getAmount(), summary.getMin()));
                summary.setAvg(amount / (double)count);
            }

            processedQueue.add(transaction);
        }
    }

    @Override
    protected void checkProcessedQueue(long minimumTimestamp, final TransactionStatisticModel summary) {

        final Iterator<CreateTransactionModel> processedQueueIterator = processedQueue.iterator();
        while(processedQueueIterator.hasNext()) {

            final CreateTransactionModel transaction = processedQueueIterator.next();
            double transactionTimestamp = transaction.getTimestamp();

            if(transactionTimestamp < minimumTimestamp) {

                processedQueueIterator.remove();

                if(summary.getCount() == 0) {

                    summary.setCount(0);
                    summary.setSum(0);
                    summary.setMax(0);
                    summary.setMin(0);
                    summary.setAvg(0);
                } else {

                    long count = summary.getCount() - 1;
                    double amount = summary.getSum() - transaction.getAmount();
                    double average = count != 0 ? amount / ((double)count) : 0;

                    summary.setCount(count);
                    summary.setSum(amount);
                    summary.setMax(processedQueue.stream()
                            .map(t -> t.getAmount())
                            .max(Double::compare)
                            .orElse(0.0));
                    summary.setMin(processedQueue.stream()
                            .map(t -> t.getAmount())
                            .min(Double::compareTo)
                            .orElse(0.0));
                    summary.setAvg(average);
                }
            }
        }
    }
}
