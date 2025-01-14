package com.tmpstk;

import com.tmpstk.commands.admin.bot.ShutdownCmmd;
import com.tmpstk.commands.games.GameNumber;
import com.tmpstk.commands.tools.PingCmmd;
import com.tmpstk.lifelisteners.ReadyListner;
import com.tmpstk.lifelisteners.ShutdownListner;
import net.dv8tion.jda.api.JDA;

public class StartThings {
    public void initialize(JDA jda) {
        initializeCommands(jda);
        initializeListeners(jda);
    }


    private void initializeCommands(JDA jda) {
        jda.addEventListener(new GameNumber());
        jda.addEventListener(new PingCmmd());
        jda.addEventListener(new ShutdownCmmd());
    }

    private void initializeListeners(JDA jda) {
        jda.addEventListener(new ReadyListner());
        jda.addEventListener(new ShutdownListner());
    }
}
