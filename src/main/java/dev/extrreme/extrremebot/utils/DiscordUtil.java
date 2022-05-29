package dev.extrreme.extrremebot.utils;

import dev.extrreme.extrremebot.Main;
import net.dv8tion.jda.api.entities.User;

public class DiscordUtil {

    public static User getUserByMention(String str) {
        if (!str.startsWith("<@!") || !str.endsWith(">")) {
            return null;
        }
        String id = str.replace("<@!", "").replace(">", "");
        return DiscordUtil.getUserById(id);
    }

    public static User getUserById(String userId) {
        try {
            return Main.getBot().getJda().getUserById(userId);
        } catch (Exception ignored) {
            return null;
        }
    }
}
