package dev.extrreme.extrremebot.commands.misc;

import dev.extrreme.extrremebot.base.command.DiscordCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

import java.io.IOException;

public class StockCommand extends DiscordCommand {

    public StockCommand() {
        super("stock", "Get the price of the specified stock.");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        if (args.length < 1) {
            return false;
        }
        Stock stock = null;

        try {
            stock = YahooFinance.get(args[0]);
        } catch (IOException ignored) {}

        if (stock == null || !stock.isValid()) {
            channel.sendMessage(sender.getAsMention() + "\n Cannot find the stock '" + args[0] + "'").complete();
            return true;
        }

        StockQuote quote = stock.getQuote();

        channel.sendMessage(sender.getAsMention() + "\n" +  stock.getName() + " (" + stock.getSymbol() + ") Price: "
                + quote.getPrice() + " (" + stock.getCurrency() + ")").complete();
        return true;
    }
}
