package dev.extrreme.extrremebot.commands.music;

import dev.extrreme.extrremebot.Main;
import dev.extrreme.extrremebot.commands.BaseDiscordCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class MusicClearCommand extends BaseDiscordCommand {
    public MusicClearCommand() {
        super("clear", "Clear the queue of songs");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        Main.getBot().getMusicManager().getMusicManager(guild).clear();
        channel.sendMessage(sender.getAsMention() + "\nCleared the music queue!").queue();
        return true;
    }

    @Override
    public String getCategory() {
        return "Music";
    }
}
