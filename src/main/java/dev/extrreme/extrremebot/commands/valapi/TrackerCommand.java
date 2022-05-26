package dev.extrreme.extrremebot.commands.valapi;

import dev.extrreme.extrremebot.commands.DiscordCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class TrackerCommand extends DiscordCommand {

    public TrackerCommand() {
        super("tracker", "View the valorant tracker page of the specified user");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        if (args.length < 1) {
            channel.sendMessage(sender.getAsMention() + "\nPlease specify a valorant username").queue();
            return true;
        }

        String valoUser = args[0];
        if (!valoUser.contains("#")) {
            channel.sendMessage(sender.getAsMention() + "\nPlease specify a valid valorant username (needs #XXXXX)").queue();
            return true;
        }

        String url = "https://tracker.gg/valorant/profile/riot/" + valoUser.replace("#", "%23") + "/overview";
        MessageEmbed embed = new EmbedBuilder()
                .setTitle(valoUser + "'s Valorant Tracker Profile", url)
                .build();

        channel.sendMessage(sender.getAsMention()).setEmbeds(embed).complete();
        return true;
    }
}
