package dev.extrreme.extrremebot.utils;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

public class StocksUtility {
    public static Stock getStock(String identifier) {
        Stock stock;

        System.out.println("Attempting to get stock " + identifier);

        try {
            stock = YahooFinance.get(identifier);
        } catch (IOException e) {
            System.out.println("Error connecting to yahoo finance");
            return null;
        }

        System.out.println("Got stock:" + (stock == null ? "null" : stock.getName()));
        return (stock == null || !stock.isValid()) ? null : stock;
    }
}
