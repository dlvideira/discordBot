package com.discord.discordbot.discordListener;

import com.discord.discordbot.model.command.generic.GenericCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DiscordChatListener extends ListenerAdapter {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        // esse é o evento de receber msg no chat
        var readFirstWord = Arrays.stream(event.getMessage().getContentRaw().split(" ")).findFirst();

        if (readFirstWord.get().startsWith("!"))
            applicationEventPublisher.publishEvent(new GenericCommandEvent(event));
    }
}
