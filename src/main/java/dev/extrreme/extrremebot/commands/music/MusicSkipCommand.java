package dev.extrreme.extrremebot.commands.music;

import dev.extrreme.extrremebot.Main;
import dev.extrreme.extrremebot.base.command.DiscordCommand;
import net.dv8tion.jda.api.entities.*;

public class MusicSkipCommand extends DiscordCommand {
    public MusicSkipCommand() {
        super("skip", "Skip the current song");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        String current = Main.getBot().getMusicManager().getMusicManager(guild).getCurrentTrack().getInfo().title;
        Main.getBot().getMusicManager().getMusicManager(guild).next();
        channel.sendMessage(sender.getAsMention() + "\nSkipped " + current).queue();
        return true;
    }
}
