package dev.extrreme.extrremebot.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public abstract class DiscordCommand {

    protected String label;
    protected String description;

    public DiscordCommand(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public String getLabel() {
        return this.label;
    }

    public String getDescription() {
        return this.description;
    }

    public void sendArgumentsError(TextChannel channel, User user) {
        channel.sendMessage(user.getAsMention() + "\n Error, please check your arguments and try again.").complete();
    }

    public abstract boolean execute(Guild guild, TextChannel channel, User sender, String... args);

}
