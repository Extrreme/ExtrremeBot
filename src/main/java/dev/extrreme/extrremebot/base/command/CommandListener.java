package dev.extrreme.extrremebot.base.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CommandListener extends ListenerAdapter {

    private final CommandManager manager;

    public CommandListener(CommandManager manager) {
        this.manager = manager;
    }
    
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (!message.startsWith(manager.getPrefix())) {
            return;
        }

        String[] args = message.split(" ");
        String command = args[0].replaceFirst(manager.getPrefix(), "");

        manager.onCommand(event.getGuild(), event.getTextChannel(), event.getAuthor(), command,
                Arrays.copyOfRange(args, 1, args.length));
    }
}
