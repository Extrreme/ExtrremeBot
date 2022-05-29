package dev.extrreme.extrremebot;

import dev.extrreme.extrremebot.sql.MySQL;
import dev.extrreme.extrremebot.sql.SQLManager;

import javax.security.auth.login.LoginException;

public class Main {
    private static ExtrremeBot bot;
    private static SQLManager sql;

    public static void main(String[] args) throws LoginException {
        bot = new ExtrremeBot();

        String host = System.getenv("SQL_HOST") + ":" + System.getenv("SQL_PORT");
        String database = System.getenv("SQL_USER");

        String user = System.getenv("SQL_USER");
        String pass = System.getenv("SQL_PASS");

        String sqlUrl = MySQL.genURL(host, database);

        sql = new SQLManager(new MySQL(sqlUrl, user, pass));
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
