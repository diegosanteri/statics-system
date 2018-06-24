package de.n26.transaction.statistic.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateTransactionModel {

    private final double amount;
    private final long timestamp;

    @JsonCreator
    public CreateTransactionModel(
            @JsonProperty(value = "amount", required = true) final double amount,
            @JsonProperty(value = "timestamp", required = true) final long timestamp) {

        this.amount = amount;
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
