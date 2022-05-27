package dev.extrreme.extrremebot;

import dev.extrreme.extrremebot.base.DiscordBot;
import dev.extrreme.extrremebot.audio.MusicManagerManager;
import dev.extrreme.extrremebot.commands.misc.HelpCommand;
import dev.extrreme.extrremebot.commands.misc.StockCommand;
import dev.extrreme.extrremebot.commands.music.MusicPlayCommand;
import dev.extrreme.extrremebot.commands.music.MusicRepeatCommand;
import dev.extrreme.extrremebot.commands.music.MusicSkipCommand;
import dev.extrreme.extrremebot.commands.valorant.TrackerCommand;

public class ExtrremeBot extends DiscordBot {

    private final MusicManagerManager musicManager;

    public ExtrremeBot() {
        super(System.getenv("TOKEN"));

        musicManager = new MusicManagerManager();

        registerCommand(new HelpCommand());

        registerCommand(new StockCommand());

        registerCommand(new MusicPlayCommand());
        registerCommand(new MusicSkipCommand());
        registerCommand(new MusicRepeatCommand());

        registerCommand(new TrackerCommand());
    }

    public MusicManagerManager getMusicManager() {
        return this.musicManager;
    }
}
