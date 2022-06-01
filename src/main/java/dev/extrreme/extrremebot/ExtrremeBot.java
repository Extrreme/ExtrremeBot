package dev.extrreme.extrremebot;

import dev.extrreme.extrremebot.base.DiscordBot;
import dev.extrreme.extrremebot.audio.MusicManagerManager;
import dev.extrreme.extrremebot.commands.misc.HelpCommand;
import dev.extrreme.extrremebot.commands.misc.StockCommand;
import dev.extrreme.extrremebot.commands.music.*;
import dev.extrreme.extrremebot.commands.valorant.SetValorantAccountCommand;
import dev.extrreme.extrremebot.commands.valorant.TrackerValorantCommand;
import dev.extrreme.extrremebot.commands.valorant.ViewValorantAccountCommand;

import javax.security.auth.login.LoginException;

public class ExtrremeBot extends DiscordBot {
    public static final String VAL_ACCOUNT_TABLE = "valorantAccounts";

    private final MusicManagerManager musicManager;

    public ExtrremeBot() throws LoginException {
        super(System.getenv("TOKEN"));

        musicManager = new MusicManagerManager();

        Main.getSQLManager().createTable(VAL_ACCOUNT_TABLE, new String[]{"discordId", "valorantId"},
                new String[]{"varchar(255) NOT NULL PRIMARY KEY", "VARCHAR(255)"});

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

        registerCommand(new TrackerValorantCommand());
        registerCommand(new SetValorantAccountCommand());
        registerCommand(new ViewValorantAccountCommand());
    }

    public MusicManagerManager getMusicManager() {
        return this.musicManager;
    }
}
