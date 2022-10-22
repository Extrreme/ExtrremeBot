package dev.extrreme.extrremebot.userdata;

import dev.extrreme.extrremebot.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

public class UserDataManager {

    @NotNull
    public static UserData load(long userId, long guildId) {
        UserData data = Main.getDatabase().getMapper().load(UserData.class, userId, guildId);

        if (data == null) {
            data = UserData.newData(userId, guildId);
            Main.getDatabase().getMapper().save(data);
        }

        return data;
    }

    @NotNull
    public static UserData load(User user, Guild guild) {
        return load(user.getIdLong(), guild.getIdLong());
    }

    public static void delete(long userId, long guildId) {
        Main.getDatabase().getMapper().delete(UserData.newData(userId, guildId));
    }

    public static void delete(User user, Guild guild) {
        delete(user.getIdLong(), guild.getIdLong());
    }
}
