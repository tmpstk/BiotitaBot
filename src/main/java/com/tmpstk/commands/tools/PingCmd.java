package com.tmpstk.commands.tools;

import com.tmpstk.utils.Utils;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.function.Consumer;

public class PingCmd extends ListenerAdapter
{
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        if (!message.getContentRaw().split(" ")[0].equals(Utils.getPrefix())) return;
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals(Utils.getPrefix() + " ping"))
        {
            MessageChannel channel = event.getChannel();
            Consumer<Message> callback = (response) -> System.out.printf("Sent Message %s\n", response);

            channel.sendMessage("Pong!").queue(callback); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
    }
}