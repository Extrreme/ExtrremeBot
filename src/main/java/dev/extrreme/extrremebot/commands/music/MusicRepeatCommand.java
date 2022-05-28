package dev.extrreme.extrremebot.commands.music;

import dev.extrreme.extrremebot.Main;
import dev.extrreme.extrremebot.base.command.DiscordCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class MusicRepeatCommand extends DiscordCommand {

    public MusicRepeatCommand() {
        super("repeat", "Toggle repeating the last played song on/off!");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        if (args.length < 1) {
            channel.sendMessage(sender.getAsMention() + "\nPlease specify on or off!").queue();
            return true;
        }
        if (args[0].equalsIgnoreCase("on")) {
            Main.getBot().getMusicManager().getMusicManager(channel.getGuild()).setRepeat(true);
            channel.sendMessage(sender.getAsMention() + "\nIndefinetly repeating the last queued track!").queue();
        } else if (args[0].equalsIgnoreCase("off")) {
            Main.getBot().getMusicManager().getMusicManager(channel.getGuild()).setRepeat(false);
            channel.sendMessage(sender.getAsMention() + "\nStopped repeating the last queued track!").queue();
        }
        return true;
    }
}
