package de.n26.transaction.statistic.service;

import de.n26.transaction.statistic.model.CreateTransactionModel;
import de.n26.transaction.statistic.repository.TransactionRepository;
import de.n26.transaction.statistic.util.TimestampUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TransactionServiceImplTest {

    @Mock
    protected TransactionQueueService transactionQueueService;

    @Mock
    protected TransactionRepository transactionRepository;

    @InjectMocks
    protected TransactionServiceImpl transactionServiceImpl;

    @Test
    public void insertTransactionTransactionSuccessTest() {

        long timestamp = TimestampUtils.generateTimestampUnix();
        CreateTransactionModel CreateTransactionModel = new CreateTransactionModel(12.2, timestamp);
        Mockito.when(transactionQueueService.notifyTransaction(CreateTransactionModel)).thenReturn(true);
        boolean response = transactionServiceImpl.insertTransaction(CreateTransactionModel);
        Assert.assertEquals("The response from insertTransaction is false instead of true", true, response);
    }

    @Test
    public void insertTransactionTransactionWithOldTimestampErrorTest() {

        long timestamp = TimestampUtils.generateTimestampUnix();
        CreateTransactionModel CreateTransactionModel = new CreateTransactionModel(12.2, 111111111);
        Mockito.when(transactionQueueService.notifyTransaction(CreateTransactionModel)).thenReturn(true);
        boolean response = transactionServiceImpl.insertTransaction(CreateTransactionModel);
        Assert.assertEquals("The response from insertTransaction is true instead of false", false, response);
    }
}
