package dev.extrreme.extrremebot.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import dev.extrreme.extrremebot.Main;
import dev.extrreme.extrremebot.base.command.DiscordCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.concurrent.BlockingQueue;

public class MusicQueueCommand extends DiscordCommand {

    public MusicQueueCommand() {
        super("queue", "View the music queue");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        BlockingQueue<AudioTrack> queue = Main.getBot().getMusicManager().getMusicManager(channel.getGuild()).getQueue();

        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("Music Queue");
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (AudioTrack track : queue) {
            if (i > 10) {
                break;
            }
            AudioTrackInfo info = track.getInfo();
            String line = i + ". " + info.title + " - " + info.author + "\n";
            sb.append(line);
            i++;
        }

        eb.setFooter(sb.toString());

        channel.sendMessage(sender.getAsMention()).setEmbeds(eb.build()).complete();
        return true;
    }
}
