package com.discord.discordbot.model.command.generic;

import lombok.Getter;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.context.ApplicationEvent;

public class GenericCommandEvent extends ApplicationEvent {
    @Getter
    GuildMessageReceivedEvent event;

    public GenericCommandEvent(Object source) {
        super(source);
        event = (GuildMessageReceivedEvent) source;
    }
}
