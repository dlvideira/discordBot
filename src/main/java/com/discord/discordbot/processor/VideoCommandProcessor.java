package com.discord.discordbot.processor;

import com.discord.discordbot.model.command.generic.GenericCommandEvent;
import com.discord.discordbot.model.command.video.Video;
import com.discord.discordbot.model.command.video.VideoResponse;
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
import java.util.List;
import java.util.Random;

import static java.lang.String.format;
import static org.springframework.http.HttpMethod.GET;

@Component
public class VideoCommandProcessor implements CommandProcessor {
    @Value("${image.token}")
    private String imageToken;

    @Value("${image.videoUrl}")
    private String videoUrl;

    private int page = new Random().nextInt(10) + 1;

    @Override
    public void processCommand(GenericCommandEvent command) {

        String[] message = command.getEvent().getMessage().getContentRaw().split(" ");

        String searchQuery = message[1];

        if (message[0].equals("!video") && !command.getEvent().getAuthor().isBot()) {

            ResponseEntity<VideoResponse> response = getVideoFromUrl(searchQuery);

            if (response.getStatusCode().equals(HttpStatus.OK) && !response.getBody().getVideos().isEmpty()) {
                var video = getVideoResponse(response.getBody(), searchQuery);
                command.getEvent().getChannel().sendMessage(video).queue();
            } else {
                //TODO validar se é a melhor opção.
                page = 1;
                processCommand(command);
            }
        }
    }

    @NotNull
    private ResponseEntity<VideoResponse> getVideoFromUrl(String searchQuery) {
        var url = format(videoUrl, searchQuery, 1);

        HttpEntity<String> headers = new HttpEntity<>(getHeaders());
        RestTemplate restTemplate = new RestTemplateBuilder().build();

        return restTemplate.exchange(url, GET, headers, VideoResponse.class);
    }

    private MessageEmbed getVideoResponse(VideoResponse response, String searchQuery) {
        var selectedImage = selectRandomVideoFromResult(response.getVideos());

        EmbedBuilder messageEmbed = new EmbedBuilder()//TODO formatar saida, Discord nao faz preview
                // .setAuthor("Photo by: \n" + selectedImage.getPhotographer(), selectedImage.getPhotographer_url())
                // .setTitle(capitalize(searchQuery))
                // .setImage(selectedImage.getSrc().getLarge())
                // .setFooter("Image hosted by Pexels", "https://theme.zdassets.com/theme_assets/9028340/1e73e5cb95b89f1dce8b59c5236ca1fc28c7113b.png")
                // .setTimestamp(new Date().toInstant())
                .addField("test", selectedImage.getVideo_files().get(0).getLink(), false)
                .setColor(Color.GREEN);
        return messageEmbed.build();
    }

    private Video selectRandomVideoFromResult(List<Video> result) {
        return result.get(new Random().nextInt(result.size()));
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "application/json");
        headers.set("Authorization", imageToken);
        return headers;
    }
}
