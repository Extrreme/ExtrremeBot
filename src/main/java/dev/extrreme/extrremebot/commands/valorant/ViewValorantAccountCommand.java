package dev.extrreme.extrremebot.commands.valorant;

import dev.extrreme.extrremebot.ExtrremeBot;
import dev.extrreme.extrremebot.Main;
import dev.extrreme.extrremebot.base.command.DiscordCommand;
import dev.extrreme.extrremebot.utils.DiscordUtil;
import dev.extrreme.extrremebot.utils.StringUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class ViewValorantAccountCommand extends DiscordCommand {

    public ViewValorantAccountCommand() {
        super("valaccount", "View the linked valorant id of the specified user");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        User target;
        if (args.length < 1) {
            target = sender;
        } else {
            target = DiscordUtil.getUserByMention(args[0]);
        }

        if (target == null) {
            return false;
        }

        String valoUser = getLinkedValorantId(target);

        if (valoUser != null) {
            channel.sendMessage(sender.getAsMention() + "\n" +
                    (args.length < 1 ? "Your" : target.getAsMention() + "'s")
                    + " linked valorant id is " + valoUser).queue();
        } else {
            channel.sendMessage(sender.getAsMention() + "\n" +
                    (args.length < 1 ? "You do" : target.getAsMention() + " does")
                    + " not have a linked valorant id").queue();
        }

        return true;
    }

    public static String getLinkedValorantId(User user) {
        return (String) Main.getSQLManager().searchTable(ExtrremeBot.VAL_ACCOUNT_TABLE, "discordId",
                user.getId(), "valorantId");
    }
}
