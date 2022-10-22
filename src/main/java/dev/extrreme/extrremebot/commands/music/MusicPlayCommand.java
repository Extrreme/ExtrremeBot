package dev.extrreme.extrremebot.commands.music;

import dev.extrreme.extrremebot.Main;
import dev.extrreme.extrremebot.commands.BaseDiscordCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.HashMap;
import java.util.Map;

public class MusicPlayCommand extends BaseDiscordCommand {
    private final Map<String, String> defaults = new HashMap<>();

    public MusicPlayCommand() {
        super("play", "Plays a song! (Needs a YouTube url)");

        defaults.put("oceanman", "https://www.youtube.com/watch?v=vcaPiiFZu2o");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        if (args.length < 1 || (!isYoutubeURL(args[0]) && defaults.get(args[0]) == null)) {
            channel.sendMessage(sender.getAsMention() + "\nPlease specify a youtube url!").queue();
            return true;
        }

        String url = isYoutubeURL(args[0]) ? args[0] : defaults.get(args[0]);

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

        Main.getBot().getMusicManager().loadAndPlay(channel, url);
        return true;
    }

    private boolean isYoutubeURL(String url) {
        return url.contains("youtube") || url.contains("youtu.be");
    }

    @Override
    public String getCategory() {
        return "Music";
    }
}
