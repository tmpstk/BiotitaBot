package com.tmpstk;

import com.tmpstk.commands.games.gameNumber;
import com.tmpstk.commands.tools.ping;
import net.dv8tion.jda.api.JDA;

public class StartThings {
    public void initialize(JDA jda) {
        initializeCommands(jda);
    }


    private void initializeCommands(JDA jda) {
        jda.addEventListener(new gameNumber());
        jda.addEventListener(new ping());

    }
}
