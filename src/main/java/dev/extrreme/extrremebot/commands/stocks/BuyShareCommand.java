package dev.extrreme.extrremebot.commands.stocks;

import dev.extrreme.extrremebot.commands.BaseDiscordCommand;
import dev.extrreme.extrremebot.stocks.StockPortfolio;
import dev.extrreme.extrremebot.userdata.UserDataManager;
import dev.extrreme.extrremebot.utils.NumberUtility;
import dev.extrreme.extrremebot.utils.StocksUtility;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import yahoofinance.Stock;
import yahoofinance.quotes.stock.StockQuote;

public class BuyShareCommand extends BaseDiscordCommand {
    public BuyShareCommand() {
        super("buy", "Buy shares of a stock");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        if (args.length < 2) {
            return false;
        }

        Stock stock = StocksUtility.getStock(args[0]);

        if (stock == null) {
            channel.sendMessage(sender.getAsMention() + "\nCannot find the stock '" + args[0] + "'").queue();
            return true;
        }

        StockQuote quote = stock.getQuote();

        if (!NumberUtility.isDouble(args[1])) {
            channel.sendMessage(sender.getAsMention() + "Please specify a valid quantity of shares to buy").queue();
            return true;
        }

        double shares = Double.parseDouble(args[1]);
        double price = shares*quote.getPrice().doubleValue();

        UserDataManager.doWhileLoaded(sender, guild, data -> {
            StockPortfolio portfolio = data.getPortfolio();

            if (portfolio.getBalance() - price < 0) {
                channel.sendMessage(sender.getAsMention() + "You cannot afford to buy " + shares + "x of " +
                        stock.getSymbol()).queue();
                return false;
            }

            portfolio.incrementBalance(price);
            portfolio.removeShares(stock.getSymbol(), shares);

            channel.sendMessage(sender.getAsMention() + "Successfully sold " + shares + "x of " +
                    stock.getSymbol()).queue();
            return true;
        });

        return true;
    }

    @Override
    public String getCategory() {
        return "Stocks";
    }
}
