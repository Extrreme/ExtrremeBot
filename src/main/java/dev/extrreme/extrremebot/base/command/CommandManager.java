package dev.extrreme.extrremebot.base.command;

import dev.extrreme.extrremebot.base.DiscordBot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private final String prefix;
    private final List<DiscordCommand> commands = new ArrayList<>();

    public CommandManager(DiscordBot bot, String prefix) {
        this.prefix = prefix;
        bot.registerListener(new CommandListener(this));
    }

    public CommandManager(DiscordBot bot) {
        this(bot, "!");
    }

    public String getPrefix() {
        return prefix;
    }

    public void registerCommand(DiscordCommand command) {
        commands.add(command);
    }

    public void unregisterCommand(DiscordCommand command) {
        commands.remove(command);
    }

    public List<DiscordCommand> getCommands() {
        return new ArrayList<>(commands);
    }

    public void onCommand(Guild guild, TextChannel channel, User user, String command, String... args) {
        for (DiscordCommand possibleCommand : commands) {
            if (!possibleCommand.getLabel().equalsIgnoreCase(command)) {
                continue;
            }
            if (!possibleCommand.execute(guild, channel, user, args)) {
                possibleCommand.sendArgumentsError(channel, user);
            }
        }
    }
}
