package dev.extrreme.extrremebot;

import javax.security.auth.login.LoginException;

public class Main {

    private static ExtrremeBot bot;

    public static void main(String[] args) throws LoginException {
        bot = new ExtrremeBot();
    }

    public static ExtrremeBot getBot() {
        return bot;
    }
}
