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
    public void incrementBalance(double amount) {
        this.balance+=amount;
    }
    public void decrementBalance(double amount) {
        this.balance-=amount;
    }

    public Map<String, Double> getShares() {
        return new HashMap<>(shares);
    }
    public void setShares(Map<String, Double> shares) {
        this.shares = new HashMap<>(shares);
    }
    public double getShares(String stock) {
        return shares.containsKey(stock) ? shares.get(stock) : 0;
    }
    public void addShares(String stock, double shares) {
        if (shares < 0) {
            throw new IllegalArgumentException("Cannot add negative shares");
        } else if (shares == 0) {
            return;
        }

        double curr = this.shares.containsKey(stock) ? this.shares.get(stock) : 0;
        this.shares.put(stock, curr+shares);
    }
    public void removeShares(String stock, double shares) {
        if (shares < 0) {
            throw new IllegalArgumentException("Cannot remove negative shares");
        } else if (shares == 0) {
            return;
        }

        double curr = this.shares.containsKey(stock) ? this.shares.get(stock) : 0;

        if (curr-shares > 0) {
            this.shares.put(stock, curr - shares);
        } else {
            this.shares.remove(stock);
        }
    }

    public void forEach(BiConsumer<String, Double> action) {
        getShares().forEach(action);
    }
}
