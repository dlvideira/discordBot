package com.discord.discordbot.model.command;

import lombok.Getter;

public enum AvailableCommand {
    CLIMA("**!clima**", "Uso: !clima <nome_da_cidade>"),
    TRANSLATE("**!translate**", "Uso: !translate <palavra_em_inglês> (Outros idiomas em breve!)"),
    IMAGEM("**!imagem**", "Uso: !imagem <tema_da_imagem>");

    @Getter
    private String name;
    @Getter
    public String description;

    AvailableCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
