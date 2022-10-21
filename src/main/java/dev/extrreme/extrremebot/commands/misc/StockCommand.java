package dev.extrreme.extrremebot.commands.misc;

import dev.extrreme.extrremebot.base.command.DiscordCommand;
import dev.extrreme.extrremebot.utils.StockUtility;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import yahoofinance.Stock;
import yahoofinance.quotes.stock.StockQuote;

public class StockCommand extends DiscordCommand {
    public StockCommand() {
        super("stock", "Get the price of the specified stock.");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        if (args.length < 1) {
            return false;
        }
        Stock stock = StockUtility.getStock(args[0]);

        if (stock == null) {
            channel.sendMessage(sender.getAsMention() + "\nCannot find the stock '" + args[0] + "'").queue();
            return true;
        }

        StockQuote quote = stock.getQuote();

        channel.sendMessage(sender.getAsMention() + "\n" +  stock.getName() + " (" + stock.getSymbol() + ") Price: "
                + quote.getPrice() + " (" + stock.getCurrency() + ")").queue();
        return true;
    }
}
