package dev.extrreme.extrremebot.commands.misc;

import dev.extrreme.extrremebot.Main;
import dev.extrreme.extrremebot.base.command.DiscordCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class HelpCommand extends DiscordCommand {
    public HelpCommand() {
        super("test", "List all the commands.");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        channel.sendMessage(guild.getId()).queue();
        return true;
    }
}
