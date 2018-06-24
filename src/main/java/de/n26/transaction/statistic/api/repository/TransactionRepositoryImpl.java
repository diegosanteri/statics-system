package de.n26.transaction.statistic.api.repository;

import de.n26.transaction.statistic.api.model.TransactionStatisticModel;
import org.springframework.stereotype.Repository;

import java.util.concurrent.atomic.AtomicReference;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final static AtomicReference<TransactionStatisticModel> statisticStored = new AtomicReference<TransactionStatisticModel>();

    public TransactionRepositoryImpl() {

        TransactionStatisticModel summary = new TransactionStatisticModel();
        summary.setSum(0);
        summary.setMax(0);
        summary.setMin(0);
        summary.setAvg(0);
        summary.setCount(0);

        statisticStored.set(summary);
    }

    @Override
    public void updateSummaryTransaction(TransactionStatisticModel transactionStatisticModel) {

        statisticStored.set(transactionStatisticModel);
    }

    @Override
    public TransactionStatisticModel findStatistics() {

        return statisticStored.get();
    }
}
