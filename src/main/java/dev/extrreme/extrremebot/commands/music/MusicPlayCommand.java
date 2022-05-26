package dev.extrreme.extrremebot.commands.music;

import dev.extrreme.extrremebot.ExtrremeBot;
import dev.extrreme.extrremebot.commands.DiscordCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.*;

import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;

public class MusicPlayCommand extends DiscordCommand implements AudioSendHandler {

    public MusicPlayCommand() {
        super("play", "Plays a song! (Needs url e.g., YouTube)");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        if (args.length < 1) {
            channel.sendMessage(sender.getAsMention() + "\nPlease specify a url!").queue();
            return true;
        }
        if (!guild.getSelfMember().hasPermission(channel, Permission.VOICE_CONNECT)) {
            channel.sendMessage(sender.getAsMention() + "\nI do not have permissions to join a voice channel!").queue();
            return true;
        }
        Member member = guild.getMember(sender);
        Member bot = guild.getSelfMember();
        if (member == null) {
            return true;
        }
        if (member.getVoiceState() == null || member.getVoiceState().getChannel() == null) {
            channel.sendMessage(sender.getAsMention() + "\nYou are not connected to a voice channel!").queue();
            return true;
        }
        AudioChannel audioChannel = member.getVoiceState().getChannel();
        if (bot.getVoiceState() == null || bot.getVoiceState().getChannel() == null
                || audioChannel != bot.getVoiceState().getChannel()) {
            channel.sendMessage(sender.getAsMention() + "\nConnected to the voice channel!").queue();
        }
        guild.getAudioManager().openAudioConnection(audioChannel);
        if (args[0].equalsIgnoreCase("oceanman") || args[0].equalsIgnoreCase("ocean-man")) {
            args[0] = "https://www.youtube.com/watch?v=vcaPiiFZu2o";
        }
        ExtrremeBot.musicManager.loadAndPlay(channel, args[0]);
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
