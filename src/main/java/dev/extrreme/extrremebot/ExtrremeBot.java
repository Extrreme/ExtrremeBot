package dev.extrreme.extrremebot;

import dev.extrreme.extrremebot.base.DiscordBot;
import dev.extrreme.extrremebot.audio.MusicManagerManager;
import dev.extrreme.extrremebot.commands.misc.HelpCommand;
import dev.extrreme.extrremebot.commands.misc.StockCommand;
import dev.extrreme.extrremebot.commands.music.*;
import dev.extrreme.extrremebot.commands.valorant.TrackerCommand;
import dev.extrreme.extrremebot.sql.MySQL;
import dev.extrreme.extrremebot.sql.SQLManager;
import dev.extrreme.extrremebot.utils.StringUtils;

import javax.security.auth.login.LoginException;

public class ExtrremeBot extends DiscordBot {

    private final MusicManagerManager musicManager;

    private SQLManager sqlManager;

    public ExtrremeBot() throws LoginException {
        super(System.getenv("TOKEN"));

        musicManager = new MusicManagerManager();

        String host = System.getenv("SQL_HOST") + ":" + System.getenv("SQL_PORT");
        String database = System.getenv("SQL_USER");

        String user = System.getenv("SQL_USER");
        String pass = System.getenv("SQL_PASS");

        Main.log(StringUtils.concatenate(new String[]{host, database, user, pass}, ", "));
        String sqlUrl = MySQL.genURL(host, database);
        Main.log(sqlUrl);
        sqlManager = new SQLManager(new MySQL(sqlUrl, user, pass));

        boolean test = sqlManager.createTable("test", new String[]{"testA, testB"},
                new String[]{"varchar(255) NOT NULL PRIMARY KEY", "VARCHAR(255)"});
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
