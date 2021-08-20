package com.discord.discordbot.processor;

import com.discord.discordbot.model.command.generic.GenericCommandEvent;
import com.discord.discordbot.model.command.image.Image;
import com.discord.discordbot.model.command.image.ImageResponse;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static java.lang.String.format;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.util.StringUtils.capitalize;

@Component
public class ImageCommandProcessor implements CommandProcessor{
    @Value("${image.token}")
    private String imageToken;

    @Value("${image.url}")
    private String imageUrl;

    private int page = new Random().nextInt(10) + 1;

    @Override
    public void processCommand(GenericCommandEvent command) {

        String[] message = command.getEvent().getMessage().getContentRaw().split(" ");

        String searchQuery = message[1];

        if (message[0].equals("!imagem") && !command.getEvent().getAuthor().isBot()) {

            ResponseEntity<ImageResponse> response = getImageFromUrl(searchQuery);

            if (response.getStatusCode().equals(HttpStatus.OK) && !response.getBody().getPhotos().isEmpty()) {
                var image = getImageResult(response.getBody(), searchQuery);
                command.getEvent().getChannel().sendMessage(image).queue();
            } else {
                //TODO validar se é a melhor opção.
                page = 1;
                processCommand(command);
            }
        }
    }

    @NotNull
    private ResponseEntity<ImageResponse> getImageFromUrl(String searchQuery) {
        var url = format(imageUrl, searchQuery, page);

        HttpEntity<String> headers = new HttpEntity<>(getHeaders());
        RestTemplate restTemplate = new RestTemplateBuilder().build();

        return restTemplate.exchange(url, GET, headers, ImageResponse.class);
    }

    private MessageEmbed getImageResult(ImageResponse response, String searchQuery) {
        var selectedImage = selectRandomImageFromResult(response.getPhotos());

        EmbedBuilder messageEmbed = new EmbedBuilder()
                .setAuthor("Photo by: \n" + selectedImage.getPhotographer(), selectedImage.getPhotographer_url())
                .setTitle(capitalize(searchQuery))
                .setImage(selectedImage.getSrc().getLarge())
                .setFooter("Image hosted by Pexels", "https://theme.zdassets.com/theme_assets/9028340/1e73e5cb95b89f1dce8b59c5236ca1fc28c7113b.png")
                .setTimestamp(new Date().toInstant())
                .setColor(Color.GREEN);
        return messageEmbed.build();
    }

    private Image selectRandomImageFromResult(List<Image> result) {
        return result.get(new Random().nextInt(result.size()));
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "application/json");
        headers.set("Authorization", imageToken);
        return headers;
    }
}