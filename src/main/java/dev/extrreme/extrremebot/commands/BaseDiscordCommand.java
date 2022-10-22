package dev.extrreme.extrremebot.commands;

import dev.extrreme.extrremebot.base.command.DiscordCommand;

public abstract class BaseDiscordCommand extends DiscordCommand {
    public BaseDiscordCommand(String label, String description) {
        super(label, description);
    }
    
    public abstract String getCategory();
}
