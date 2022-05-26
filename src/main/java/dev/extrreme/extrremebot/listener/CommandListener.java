package dev.extrreme.extrremebot.listener;

import dev.extrreme.extrremebot.ExtrremeBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (!message.startsWith("!")) {
            return;
        }

        String[] args = message.split(" ");
        String command = args[0].replaceFirst("!", "");

        ExtrremeBot.commandManager.onCommand(event.getGuild(), event.getTextChannel(), event.getAuthor(), command,
                Arrays.copyOfRange(args, 1, args.length));
    }
}
