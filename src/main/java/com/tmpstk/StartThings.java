package com.tmpstk;

import com.tmpstk.commands.gameNumber;
import com.tmpstk.commands.ping;
import net.dv8tion.jda.api.JDA;

public class StartThings {
    public void initialize(JDA jda) {
        jda.addEventListener(new ping());
        jda.addEventListener(new gameNumber());
    }
}
