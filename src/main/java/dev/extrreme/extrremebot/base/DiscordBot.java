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

    private void start() throws LoginException {
        this.jda = JDABuilder.createDefault(token).build();
        this.commandManager = new CommandManager();
        this.jda.addEventListener(new CommandListener(this.commandManager));
    }

    public void registerCommand(DiscordCommand command) {
        this.commandManager.registerCommand(command);
    }

    public void unregisterCommand(DiscordCommand command) {
        this.commandManager.unregisterCommand(command);
    }

    public void registerListener(ListenerAdapter listener) {
        if (this.jda != null) {
            this.jda.addEventListener(listener);
        }
    }

    public void unregisterListener(ListenerAdapter listener) {
        if (this.jda != null) {
            this.jda.removeEventListener(listener);
        }
    }

    public JDA getJda() {
        return this.jda;
    }

    public List<DiscordCommand> getCommands() {
        return this.commandManager.getCommands();
    }
}
