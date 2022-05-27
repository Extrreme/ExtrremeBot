package dev.extrreme.extrremebot.commands.music;

import dev.extrreme.extrremebot.Main;
import dev.extrreme.extrremebot.base.command.DiscordCommand;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.*;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;

public class MusicSkipCommand extends DiscordCommand implements AudioSendHandler {

    public MusicSkipCommand() {
        super("skip", "Skip the current song");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        Main.getBot().getMusicManager().getMusicManager(guild).next();
        return true;
    }

    @Override
    public boolean canProvide() {
        return false;
    }

    @Nullable
    @Override
    public ByteBuffer provide20MsAudio() {
        return null;
    }
}
