package dev.extrreme.extrremebot;

import dev.extrreme.extrremebot.base.DiscordBot;
import dev.extrreme.extrremebot.audio.MusicManager;
import dev.extrreme.extrremebot.commands.misc.HelpCommand;
import dev.extrreme.extrremebot.commands.stocks.PortfolioCommand;
import dev.extrreme.extrremebot.commands.stocks.StockCommand;
import dev.extrreme.extrremebot.commands.music.*;
import dev.extrreme.extrremebot.listener.GuildListener;
import dev.extrreme.extrremebot.utils.StocksUtility;

import javax.security.auth.login.LoginException;

public class ExtrremeBot extends DiscordBot {
    private final MusicManager musicManager;

    public ExtrremeBot() throws LoginException {
        super(System.getenv("TOKEN"));

        musicManager = new MusicManager();

        registerListeners();
        registerCommands();

        System.out.println("ExtrremeBot successfully started");
    }

    private void registerListeners() {
        registerListener(new GuildListener());
    }

    private void registerCommands() {
        registerCommand(new HelpCommand());

        registerCommand(new StockCommand());
        registerCommand(new PortfolioCommand());

        registerCommand(new MusicPlayCommand());
        registerCommand(new MusicSkipCommand());
        registerCommand(new MusicRepeatCommand());
        registerCommand(new MusicClearCommand());
        registerCommand(new MusicQueueCommand());
    }

    public MusicManager getMusicManager() {
        return musicManager;
    }
}
