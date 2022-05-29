package dev.extrreme.extrremebot.commands.valorant;

import dev.extrreme.extrremebot.ExtrremeBot;
import dev.extrreme.extrremebot.Main;
import dev.extrreme.extrremebot.base.command.DiscordCommand;
import dev.extrreme.extrremebot.utils.StringUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class SetValorantAccountCommand extends DiscordCommand {
    public SetValorantAccountCommand() {
        super("setvalaccount", "Set the valorant account associated with your discord account");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        if (args.length < 1) {
            channel.sendMessage(sender.getAsMention() + "\nPlease specify a valorant username").queue();
            return true;
        }

        String valoUser = StringUtils.concatenate(args, " ");
        if (!valoUser.contains("#")) {
            channel.sendMessage(sender.getAsMention() + "\nPlease specify a valid valorant username (needs #XXXXX)").queue();
            return true;
        }

        boolean success = Main.getSQLManager().setInTable(ExtrremeBot.VAL_ACCOUNT_TABLE, "discordId", sender.getId(),
                "valorantId", valoUser);

        if (success) {
            channel.sendMessage(sender.getAsMention() + "\nSuccessfully set your associated valorant account to " +
                    "**" + valoUser + "**").queue();
        } else {
            channel.sendMessage(sender.getAsMention() + "\nAn error occurred, please try again later.").queue();
        }

        return true;
    }
}
