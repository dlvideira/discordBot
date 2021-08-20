package com.discord.discordbot.processor;

import com.discord.discordbot.model.command.generic.GenericCommandEvent;

public interface CommandProcessor {
    void processCommand(GenericCommandEvent command);
}
