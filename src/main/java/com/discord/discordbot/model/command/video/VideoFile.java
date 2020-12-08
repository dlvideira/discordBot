package com.discord.discordbot.model.command.video;

import lombok.Data;

@Data
public class VideoFile {
    private int id;
    private String quality;
    private String file_type;
    private int width;
    private int height;
    private String link;
}