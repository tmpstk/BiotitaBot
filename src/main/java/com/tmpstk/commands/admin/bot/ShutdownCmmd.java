package com.tmpstk.commands.admin.bot;

import com.tmpstk.utils.Utils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.function.Consumer;

public class ShutdownCmmd extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (!event.getGuild().getId().equals(Utils.getMyServerId())) return;
        Message message = event.getMessage();
        if (!message.getContentRaw().split(" ")[0].equals(Utils.getPrefix())) return;
        String content = message.getContentRaw();
        if (content.equals(Utils.getPrefix() + " shutdown"))
        {
            Member member = event.getMember();
            if (member.hasPermission(Permission.ADMINISTRATOR)){
                MessageChannel channel = event.getChannel();
                Consumer<Message> callback = (response) -> System.out.printf("Called: %s\n", response);

                channel.sendMessage("Stopping the bot...").queue(callback);
                JDA jda = event.getJDA();
                jda.shutdown();
            } else {
                Consumer<Message> callback = (response) -> System.out.printf("Warning: " + member.getId() + " tried to shutdown the bot");
                event.getChannel().sendMessage("You must be an administrator to shutdown the bot").queue(callback);
            }
        }
    }
}
