package dev.extrreme.extrremebot;

import dev.extrreme.extrremebot.audio.MusicManagerManager;
import dev.extrreme.extrremebot.commands.misc.HelpCommand;
import dev.extrreme.extrremebot.commands.misc.StockCommand;
import dev.extrreme.extrremebot.commands.CommandManager;
import dev.extrreme.extrremebot.commands.music.MusicPlayCommand;
import dev.extrreme.extrremebot.commands.music.MusicRepeatCommand;
import dev.extrreme.extrremebot.commands.music.MusicSkipCommand;
import dev.extrreme.extrremebot.commands.valorant.TrackerCommand;
import dev.extrreme.extrremebot.listener.CommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class ExtrremeBot {

    public static CommandManager commandManager;
    public static MusicManagerManager musicManager;
    public static JDA jda;

    public static void main(String[] args) {
        if (startBot()) {
            registerCommands();
            musicManager = new MusicManagerManager();
        }
    }

    private static void registerCommands() {
        commandManager = new CommandManager();

        jda.addEventListener(new CommandListener());

        commandManager.registerCommand(new HelpCommand());

        commandManager.registerCommand(new StockCommand());

        commandManager.registerCommand(new MusicPlayCommand());
        commandManager.registerCommand(new MusicSkipCommand());
        commandManager.registerCommand(new MusicRepeatCommand());

        commandManager.registerCommand(new TrackerCommand());
    }

    private static boolean startBot() {
        try {
            jda = JDABuilder.createDefault(System.getenv("TOKEN")).build();
        } catch (LoginException e) {
            System.out.println("Bot failed to login! Invalid token?");
            return false;
        }
        return true;
    }
}
