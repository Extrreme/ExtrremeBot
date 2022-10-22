package dev.extrreme.extrremebot.listener;

import dev.extrreme.extrremebot.userdata.UserDataManager;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class GuildListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        UserDataManager.load(event.getUser(), event.getGuild());
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        UserDataManager.delete(event.getUser(), event.getGuild());
    }
}
