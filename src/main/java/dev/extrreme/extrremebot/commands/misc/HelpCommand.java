package dev.extrreme.extrremebot.commands.misc;

import dev.extrreme.extrremebot.Main;
import dev.extrreme.extrremebot.base.command.DiscordCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class HelpCommand extends DiscordCommand {

    public HelpCommand() {
        super("help", "List all the commands.");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        StringBuilder toSend = new StringBuilder("**Commands:** \n");
        for (DiscordCommand discordCommand : Main.getBot().getCommands()) {
            if (discordCommand.getLabel().equalsIgnoreCase("help")) {
                continue;
            }
            toSend.append("!").append(discordCommand.getLabel())
                    .append(" - ")
                    .append(discordCommand.getDescription())
                    .append("\n");
        }
        channel.sendMessage(sender.getAsMention() + "\n" + toSend).complete();
        return true;
    }
}
