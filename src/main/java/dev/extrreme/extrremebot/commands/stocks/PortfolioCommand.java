package dev.extrreme.extrremebot.commands.stocks;

import dev.extrreme.extrremebot.commands.BaseDiscordCommand;
import dev.extrreme.extrremebot.stocks.StockPortfolio;
import dev.extrreme.extrremebot.userdata.UserData;
import dev.extrreme.extrremebot.userdata.UserDataManager;
import dev.extrreme.extrremebot.utils.StocksUtility;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import yahoofinance.Stock;

import java.awt.*;
import java.util.concurrent.atomic.DoubleAdder;

public class PortfolioCommand extends BaseDiscordCommand {
    public PortfolioCommand() {
        super("portfolio", "View your stock portfolio");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        UserData data = UserDataManager.load(sender.getIdLong(), guild.getIdLong());
        StockPortfolio portfolio = data.getPortfolio();

        StringBuilder sb = new StringBuilder();
        sb.append("__Available Capital:__ $").append(portfolio.getBalance()).append("\n\n");

        DoubleAdder totalValue = new DoubleAdder();
        totalValue.add(portfolio.getBalance());

        sb.append("__Shares:__\n");
        if (portfolio.size() > 0) {
            portfolio.forEach((symbol, shares) -> {
                Stock stock = StocksUtility.getStock(symbol);
                if (stock == null) {
                    return;
                }
                double value = stock.getQuote().getPrice().doubleValue() * shares;
                sb.append("- **").append(stock.getSymbol()).append(":** ").append(shares).append("x *($")
                        .append(value).append(")*\n");

                totalValue.add(value);
            });
        } else {
            sb.append("*N/A*\n");
        }

        sb.append("\n__**Total Value:**__ $").append(totalValue.doubleValue());

        MessageEmbed embed = new EmbedBuilder()
                .setColor(Color.WHITE)
                .setTitle("__**Your Portfolio**__")
                .setDescription(sb)
                .setFooter("__**Total Value:**__ $" + totalValue.doubleValue())
                .build();
        channel.sendMessage(sender.getAsMention()).setEmbeds(embed).queue();
        return true;
    }

    @Override
    public String getCategory() {
        return "Stocks";
    }
}
