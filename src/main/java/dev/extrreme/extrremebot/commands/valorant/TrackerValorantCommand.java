package dev.extrreme.extrremebot.commands.valorant;

import dev.extrreme.extrremebot.base.command.DiscordCommand;
import dev.extrreme.extrremebot.utils.StringUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class TrackerValorantCommand extends DiscordCommand {

    public TrackerValorantCommand() {
        super("tracker", "View the valorant tracker page of the specified user");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        if (args.length < 1 && ViewValorantAccountCommand.getLinkedValorantId(sender) == null) {
            channel.sendMessage(sender.getAsMention() + "\nPlease specify a valorant username").queue();
            return true;
        }

        String valoUser = args.length < 1 ? ViewValorantAccountCommand.getLinkedValorantId(sender) :
                StringUtils.concatenate(args, " ");
        if (!valoUser.contains("#")) {
            channel.sendMessage(sender.getAsMention() + "\nPlease specify a valid valorant username (needs #XXXXX)").queue();
            return true;
        }

        String valoUserFormatted = valoUser.replace("#", "%23").replace(" ", "%20");
        String url = "https://tracker.gg/valorant/profile/riot/" + valoUserFormatted + "/overview";

        MessageEmbed embed = new EmbedBuilder()
                .setTitle(valoUser + "'s Valorant Tracker Profile", url)
                .build();

        channel.sendMessage(sender.getAsMention()).setEmbeds(embed).complete();
        return true;
    }
}
