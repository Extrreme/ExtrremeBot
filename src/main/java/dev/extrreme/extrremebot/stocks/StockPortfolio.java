package dev.extrreme.extrremebot.stocks;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@DynamoDBDocument
public class StockPortfolio {
    private double balance = 10000;
    private Map<String, Double> shares = new HashMap<>();

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Map<String, Double> getShares() {
        return new HashMap<>(shares);
    }

    public void setShares(Map<String, Double> shares) {
        this.shares = new HashMap<>(shares);
    }

    public void addShares(String stock, double shares) {
        if (shares < 0) {
            throw new IllegalArgumentException("Cannot add negative shares");
        } else if (shares == 0) {
            return;
        }

        double curr = this.shares.containsKey(stock.toUpperCase()) ? this.shares.get(stock) : 0;
        this.shares.put(stock.toUpperCase(), curr+shares);
    }

    public void forEach(BiConsumer<String, Double> action) {
        getShares().forEach(action);
    }
}
