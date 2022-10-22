package dev.extrreme.extrremebot.commands.misc;

import dev.extrreme.extrremebot.Main;
import dev.extrreme.extrremebot.base.command.DiscordCommand;
import dev.extrreme.extrremebot.commands.BaseDiscordCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;
import java.util.Map;

public class HelpCommand extends BaseDiscordCommand {
    public HelpCommand() {
        super("help", "List all the commands.");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        String prefix = Main.getBot().getCommandManager().getPrefix();
        Map<String, List<DiscordCommand>> categorized = Main.getBot().getCommandManager().getByCategories();

        StringBuilder sb = new StringBuilder("__**Commands:**__");
        for (String category : categorized.keySet()) {
            sb.append("__").append(category).append(":").append("__");
            List<DiscordCommand> commands = categorized.get(category);
            for (DiscordCommand command : commands) {
                sb.append(prefix).append(command.getLabel()).append(": ").append(command.getDescription());
            }
            sb.append("\n\n");
        }

        channel.sendMessage(sender.getAsMention() + "\n" + sb).queue();
        return true;
    }

    @Override
    public String getCategory() {
        return "Miscellaneous";
    }
}
