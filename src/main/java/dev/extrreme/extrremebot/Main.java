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

    public static void log(String message) {
        System.out.println("[LOG] " + message);
    }
}
