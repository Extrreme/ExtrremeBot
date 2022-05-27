package dev.extrreme.extrremebot;

public class Main {

    private static ExtrremeBot bot;

    public static void main(String[] args) {
        bot = new ExtrremeBot();
    }

    public static ExtrremeBot getBot() {
        return bot;
    }
}
