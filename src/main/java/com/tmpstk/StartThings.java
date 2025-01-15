package com.tmpstk;

import com.tmpstk.commands.SlashCommand;
import com.tmpstk.commands.admin.bot.ShutdownCmd;
import com.tmpstk.commands.admin.bot.StatusCmd;
import com.tmpstk.commands.admin.server.TimeoutCmd;
import com.tmpstk.commands.games.GameNumber;
import com.tmpstk.commands.tools.PingCmd;
import com.tmpstk.lifelisteners.ReadyListner;
import com.tmpstk.lifelisteners.ShutdownListner;
import com.tmpstk.utils.Utils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StartThings {
    public void initialize(JDA jda) {
        initializeCommands(jda);
        initializeListeners(jda);
    }


    private void initializeCommands(JDA jda) {
        initializeChatCommands(jda);
        initializeSlashCommands(jda);
    }

    private void initializeListeners(JDA jda) {
        jda.addEventListener(new ReadyListner());
        jda.addEventListener(new ShutdownListner());
    }

    private void initializeChatCommands(JDA jda) {
        jda.addEventListener(new GameNumber());
        jda.addEventListener(new PingCmd());
        jda.addEventListener(new ShutdownCmd());
    }

    private void initializeSlashCommands(JDA jda) {
        List<SlashCommand> commands = Arrays.asList(
                new StatusCmd(),
                new TimeoutCmd()
        );
        commands.forEach(jda::addEventListener);

        Objects.requireNonNull(jda.getGuildById(Utils.getMyServerId()))
                .updateCommands()
                .addCommands(commands.stream()
                        .map(SlashCommand::getCommandData)
                        .toArray(SlashCommandData[]::new))
                .queue();
    }
}
