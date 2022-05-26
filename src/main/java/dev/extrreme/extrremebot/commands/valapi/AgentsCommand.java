package dev.extrreme.extrremebot.commands.valapi;

import dev.extrreme.api.models.Agent;
import dev.extrreme.api.models.AgentAbility;
import dev.extrreme.extrremebot.ExtrremeBot;
import dev.extrreme.extrremebot.commands.DiscordCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AgentsCommand extends DiscordCommand {

    public AgentsCommand() {
        super("agents", "List all the valorant agents");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        if (args.length < 1) {
            return false;
        }
        String agentName = args[0];

        Agent agent = getAgentByName(agentName);

        if (agent == null || agent.getRole() == null) {
            channel.sendMessage("Could not find the agent **" + args[0] + "**").complete();
            return true;
        }

        StringBuilder agentData = new StringBuilder();

        String nameString = "**" + agent.getDisplayName() + "** *(" + agent.getRole().getDisplayName() + ")* \n\n";
        agentData.append(nameString);

        String descriptionString = "**Description:** " + agent.getDescription() + "\n";
        agentData.append(descriptionString);

        agentData.append("**Abilities:** \n");
        for (AgentAbility agentAbility : agent.getAbilities()) {
            String abilityString = "*" + agentAbility.getDisplayName() + ":* " + agentAbility.getDescription() + "\n";
            agentData.append(abilityString);
        }

        File imageFile = new File("temp.png");
        try {
            URL url = new URL(agent.getBustPortrait());
            ImageIO.write(ImageIO.read(url), "png", imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        channel.sendMessage(agentData.toString()).addFile(imageFile).complete();
        return true;
    }

    private Agent getAgentByName(String name) {
        Agent[] agents = ExtrremeBot.valorantApi.getAgents().getData();

        for (Agent agent : agents) {
            if (!agent.getDisplayName().equalsIgnoreCase(name)) {
                continue;
            }
            return agent;
        }

        return null;
    }
}
