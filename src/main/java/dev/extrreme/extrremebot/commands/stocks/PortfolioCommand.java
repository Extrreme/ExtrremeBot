package dev.extrreme.extrremebot.commands.stocks;

import dev.extrreme.extrremebot.commands.BaseDiscordCommand;
import dev.extrreme.extrremebot.stocks.StockPortfolio;
import dev.extrreme.extrremebot.userdata.UserData;
import dev.extrreme.extrremebot.userdata.UserDataManager;
import dev.extrreme.extrremebot.utils.StocksUtility;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import yahoofinance.Stock;

import java.util.concurrent.atomic.DoubleAdder;

public class PortfolioCommand extends BaseDiscordCommand {
    public PortfolioCommand() {
        super("portfolio", "View your stock portfolio");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        UserData data = UserDataManager.load(sender.getIdLong(), guild.getIdLong());
        StockPortfolio portfolio = data.getPortfolio();

        StringBuilder sb = new StringBuilder("__**Your Portfolio:**__\n\n");
        sb.append("__Available Capital:__ $").append(portfolio.getBalance()).append("\n\n");

        sb.append("__Shares:__\n");
        if (portfolio.size() > 0) {
            DoubleAdder totalValue = new DoubleAdder();
            portfolio.forEach((symbol, shares) -> {
                Stock stock = StocksUtility.getStock(symbol);
                if (stock == null) {
                    return;
                }
                double value = stock.getQuote().getPrice().doubleValue() * shares;
                sb.append("   __").append(stock.getSymbol()).append(":__ ").append(shares).append("x *($")
                        .append(value).append(")*\n");

                totalValue.add(value);
            });

            sb.append("\n**Total Value:** $").append(totalValue.doubleValue());
        } else {
            sb.append("*N/A*");
        }

        channel.sendMessage(sender.getAsMention() + "\n" + sb).queue();
        return true;
    }

    @Override
    public String getCategory() {
        return "Stocks";
    }
}
