package dev.extrreme.extrremebot;

import javax.security.auth.login.LoginException;

public class Main {
    private static DynamoDB database;
    private static ExtrremeBot bot;

    public static void main(String[] args) throws LoginException {
        database = new DynamoDB();
        bot = new ExtrremeBot();
    }

    public static DynamoDB getDatabase() {
        return database;
    }

    public static ExtrremeBot getBot() {
        return bot;
    }
}
