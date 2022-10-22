package dev.extrreme.extrremebot.commands.stocks;

import dev.extrreme.extrremebot.commands.BaseDiscordCommand;
import dev.extrreme.extrremebot.stocks.StockPortfolio;
import dev.extrreme.extrremebot.userdata.UserData;
import dev.extrreme.extrremebot.userdata.UserDataManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class PortfolioCommand extends BaseDiscordCommand {
    public PortfolioCommand() {
        super("portfolio", "View your stock portfolio");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        UserData data = UserDataManager.load(sender.getIdLong(), guild.getIdLong());
        StockPortfolio portfolio = data.getPortfolio();

        StringBuilder sb = new StringBuilder("__**Your Portfolio:**__\n\n");
        sb.append("Capital: $").append(portfolio.getBalance()).append("\n\n");
        portfolio.forEach((stock, shares) -> sb.append(stock).append(": ").append(shares).append("x\n"));

        channel.sendMessage(sender.getAsMention() + "\n" + sb).queue();
        return true;
    }

    @Override
    public String getCategory() {
        return "Stocks";
    }
}
