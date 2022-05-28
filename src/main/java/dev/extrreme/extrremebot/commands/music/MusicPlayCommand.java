package dev.extrreme.extrremebot.commands.music;

import dev.extrreme.extrremebot.Main;
import dev.extrreme.extrremebot.base.command.DiscordCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;

public class MusicPlayCommand extends DiscordCommand {

    public MusicPlayCommand() {
        super("play", "Plays a song! (Needs a YouTube url)");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        if (args.length < 1 || !args[0].contains("youtube")) {
            channel.sendMessage(sender.getAsMention() + "\nPlease specify a youtube url!").queue();
            return true;
        }

        if (!guild.getSelfMember().hasPermission(channel, Permission.VOICE_CONNECT)) {
            channel.sendMessage(sender.getAsMention() + "\nI do not have permission to join your voice channel!").queue();
            return true;
        }

        Member member = guild.getMember(sender);
        if (member == null) {
            return true;
        }

        if (member.getVoiceState() == null || member.getVoiceState().getChannel() == null) {
            channel.sendMessage(sender.getAsMention() + "\nYou are not connected to a voice channel!").queue();
            return true;
        }

        guild.getAudioManager().openAudioConnection(member.getVoiceState().getChannel());

        if (args[0].equalsIgnoreCase("oceanman") || args[0].equalsIgnoreCase("ocean-man")) {
            args[0] = "https://www.youtube.com/watch?v=vcaPiiFZu2o";
        } else {
            Main.getBot().getMusicManager().loadAndPlay(channel, args[0]);
        }

        return true;
    }
}
