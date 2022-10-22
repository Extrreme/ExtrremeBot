package dev.extrreme.extrremebot.utils;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

public class StocksUtility {
    public static Stock getStock(String identifier) {
        Stock stock;

        try {
            stock = YahooFinance.get(identifier);
        } catch (IOException e) {
            return null;
        }

        return stock == null || stock.isValid() ? null : stock;
    }
}
