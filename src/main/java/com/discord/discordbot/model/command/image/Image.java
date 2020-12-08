package com.discord.discordbot.model.command.image;

import lombok.Data;

@Data
public class Image {
    private int id;
    private int width;
    private int height;
    private String url;
    private String photographer;
    private String photographer_url;
    private int photographer_id;
    private ImageSrc src;
    private String liked;
}