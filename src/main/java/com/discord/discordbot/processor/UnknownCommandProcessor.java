package com.discord.discordbot.processor;

import com.discord.discordbot.model.command.AvailableCommand;
import com.discord.discordbot.model.command.generic.GenericCommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

import static java.lang.String.format;

@Component
public class UnknownCommandProcessor implements CommandProcessor{

    @Override
    public void processCommand(GenericCommandEvent command) {
        command.getEvent().getChannel().sendMessage(getAvailableCommands()).queue();
    }

    private MessageEmbed getAvailableCommands() {

        Set<AvailableCommand> availableCommands = Set.of(AvailableCommand.values());
        EmbedBuilder messageEmbed = new EmbedBuilder()
                .setTitle("Comando não encontrado")
                .addField("Veja a lista de comandos disponíveis até agora:", "", false)
                .setTimestamp(new Date().toInstant());
        availableCommands.forEach(item -> {
            messageEmbed.addField(
                    format(item.getName()), item.getDescription(), false);
        });
        return messageEmbed.build();
    }
}
