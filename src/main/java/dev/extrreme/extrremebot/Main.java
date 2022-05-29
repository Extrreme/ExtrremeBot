package dev.extrreme.extrremebot;

import dev.extrreme.extrremebot.sql.MySQL;
import dev.extrreme.extrremebot.sql.SQLManager;

import javax.security.auth.login.LoginException;

public class Main {
    private static SQLManager sql;

    private static ExtrremeBot bot;

    public static void main(String[] args) throws LoginException {
        String host = System.getenv("SQL_HOST") + ":" + System.getenv("SQL_PORT");
        String database = System.getenv("SQL_USER");

        String user = System.getenv("SQL_USER");
        String pass = System.getenv("SQL_PASS");

        String sqlUrl = MySQL.genURL(host, database);

        sql = new SQLManager(new MySQL(sqlUrl, user, pass));

        bot = new ExtrremeBot();
    }

    public static ExtrremeBot getBot() {
        return bot;
    }

    public static SQLManager getSQLManager() {
        return sql;
    }

    public static void log(String message) {
        System.out.println("[LOG] " + message);
    }
}
