package dev.extrreme.extrremebot;

import dev.extrreme.extrremebot.base.DiscordBot;
import dev.extrreme.extrremebot.audio.MusicManagerManager;
import dev.extrreme.extrremebot.commands.misc.HelpCommand;
import dev.extrreme.extrremebot.commands.misc.StockCommand;
import dev.extrreme.extrremebot.commands.music.*;
import dev.extrreme.extrremebot.commands.valorant.TrackerCommand;
import dev.extrreme.extrremebot.sql.MySQL;
import dev.extrreme.extrremebot.sql.SQLManager;

import javax.security.auth.login.LoginException;

public class ExtrremeBot extends DiscordBot {

    private final MusicManagerManager musicManager;

    private SQLManager sqlManager;

    public ExtrremeBot() throws LoginException {
        super(System.getenv("TOKEN"));

        musicManager = new MusicManagerManager();

        String sqlUrl = MySQL.genURL(System.getenv("SQL_HOST") + ":" + System.getenv("SQL_PORT"),
                System.getenv("SQL_USER"));
        sqlManager = new SQLManager(new MySQL(sqlUrl, System.getenv("SQL_USER"), System.getenv("SQL_PASS")));

        boolean test = sqlManager.createTable("test", new String[]{"test1, test2"},
                new String[]{"INT(10)", "VARCHAR(255)"});
        Main.log("SQL connection:" + test);

        registerCommand(new HelpCommand());

        registerCommand(new StockCommand());

        registerCommand(new MusicPlayCommand());
        registerCommand(new MusicSkipCommand());
        registerCommand(new MusicRepeatCommand());
        registerCommand(new MusicClearCommand());
        registerCommand(new MusicQueueCommand());

        registerCommand(new TrackerCommand());
    }

    public MusicManagerManager getMusicManager() {
        return this.musicManager;
    }
}
