package dev.extrreme.extrremebot;

import dev.extrreme.extrremebot.base.DiscordBot;
import dev.extrreme.extrremebot.audio.MusicManagerManager;
import dev.extrreme.extrremebot.commands.misc.HelpCommand;
import dev.extrreme.extrremebot.commands.misc.StockCommand;
import dev.extrreme.extrremebot.commands.music.*;
import dev.extrreme.extrremebot.commands.sql.SQLStatementCommand;
import dev.extrreme.extrremebot.commands.valorant.TrackerCommand;

import javax.security.auth.login.LoginException;

public class ExtrremeBot extends DiscordBot {

    private final MusicManagerManager musicManager;

    public ExtrremeBot() throws LoginException {
        super(System.getenv("TOKEN"));

        musicManager = new MusicManagerManager();

        registerCommands();
    }

    private void registerCommands() {
        registerCommand(new HelpCommand());

        registerCommand(new StockCommand());

        registerCommand(new MusicPlayCommand());
        registerCommand(new MusicSkipCommand());
        registerCommand(new MusicRepeatCommand());
        registerCommand(new MusicClearCommand());
        registerCommand(new MusicQueueCommand());

        registerCommand(new TrackerCommand());

        registerCommand(new SQLStatementCommand());
    }

    public MusicManagerManager getMusicManager() {
        return this.musicManager;
    }
}
