package dev.extrreme.extrremebot.commands.misc;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import dev.extrreme.extrremebot.commands.DiscordCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class TranslateCommand extends DiscordCommand {


    public TranslateCommand() {
        super("urduu", "");
    }

    @Override
    public boolean execute(Guild guild, TextChannel channel, User sender, String... args) {
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        StringBuilder sb = new StringBuilder();

        for (String arg : args) {
            sb.append(arg).append(" ");
        }

        Translation t = translate.translate(sb.toString(), Translate.TranslateOption.sourceLanguage("en"),
                Translate.TranslateOption.targetLanguage("ur"));

        channel.sendMessage(sender.getAsMention() + "\n" + t.getTranslatedText()).complete();
        return true;
    }
}
