package de.n26.transaction.statistic.api.model;

public class TransactionStatisticModel {

    private long count;
    private double sum;
    private double avg;
    private double max;
    private double min;

    public TransactionStatisticModel() {

        count = 0;
        sum = 0;
        avg = 0;
        max = 0;
        min = 0;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }
}
