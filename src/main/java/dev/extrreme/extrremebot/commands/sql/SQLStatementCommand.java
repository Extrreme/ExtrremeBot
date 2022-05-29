package dev.extrreme.extrremebot.commands.sql;

import dev.extrreme.extrremebot.Main;
import dev.extrreme.extrremebot.base.command.DiscordCommand;
import dev.extrreme.extrremebot.utils.StringUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class SQLStatementCommand extends DiscordCommand {

    public SQLStatementCommand() {
        super("sql", "Execute a MySQL statement");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        if (args.length < 1) {
            channel.sendMessage(sender.getAsMention() + "\nPlease specify a valid mysql statement").queue();
            return true;
        }

        String stmt = StringUtils.concatenate(args, " ");

        boolean success = Main.getSQLManager().execute(stmt);

        if (success) {
            channel.sendMessage(sender.getAsMention() + "\nSuccessfully executed '*" + stmt + "*'").queue();
        } else {
            channel.sendMessage(sender.getAsMention() + "\nFailed to execute '*" + stmt + "*'").queue();
        }
        return true;
    }
}
