package dev.extrreme.extrremebot.base;

import dev.extrreme.extrremebot.base.command.CommandListener;
import dev.extrreme.extrremebot.base.command.CommandManager;
import dev.extrreme.extrremebot.base.command.DiscordCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.util.List;

public class DiscordBot {

    private final String token;
    private JDA jda = null;
    private CommandManager commandManager = null;

    public DiscordBot(@NotNull String token) throws LoginException {
        this.token = token;
        start();
    }

    public void registerCommand(DiscordCommand command) {
        commandManager.registerCommand(command);
    }

    public void unregisterCommand(DiscordCommand command) {
        commandManager.unregisterCommand(command);
    }

    public void registerListener(ListenerAdapter listener) {
        if (jda != null) {
            jda.addEventListener(listener);
        }
    }

    public void unregisterListener(ListenerAdapter listener) {
        if (jda != null) {
            jda.removeEventListener(listener);
        }
    }

    private void start() throws LoginException {
        jda = JDABuilder.createDefault(token).build();
        commandManager = new CommandManager();
        jda.addEventListener(new CommandListener(commandManager));
    }

    public List<DiscordCommand> getCommands() {
        return commandManager.getCommands();
    }
}
