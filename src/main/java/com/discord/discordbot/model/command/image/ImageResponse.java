package com.discord.discordbot.model.command.image;

import lombok.Data;

import java.util.List;

@Data
public class ImageResponse {
    private String total_results;
    private String page;
    private List<Image> photos;
}
