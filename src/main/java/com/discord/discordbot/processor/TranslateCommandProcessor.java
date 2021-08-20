package com.discord.discordbot.processor;

import com.discord.discordbot.model.command.generic.GenericCommandEvent;
import com.discord.discordbot.model.command.translate.Translate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.POST;

@Component
public class TranslateCommandProcessor implements CommandProcessor {
    @Value("${translate.token}")
    private String translateToken;

    @Value("${translate.host}")
    private String translateHost;

    @Value("${translate.url}")
    private String translateUrl;

    @Override
    public void processCommand(GenericCommandEvent command) {

        String[] message = command.getEvent().getMessage().getContentRaw().split(" ");

        if (message[0].equals("!translate") && !command.getEvent().getAuthor().isBot()) {

            HttpEntity<String> request = new HttpEntity<>(String.format("q=%s&source=en&target=pt-br", message[1]), getHeaders());
            RestTemplate restTemplate = new RestTemplateBuilder().build();

            ResponseEntity<Translate> response = restTemplate.exchange(translateUrl, POST, request, Translate.class);

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                var translation = getTranslations(response);
                command.getEvent().getChannel().sendMessage(String.format("A tradução de **%s** é: **%s**", message[1], translation)).queue();
            }
        }
    }

    private String getTranslations(ResponseEntity<Translate> response) {
        //TODO retornar lista quando houver implementação de mais linguas
        return response.getBody().getData().getTranslations().get(0).getTranslatedText();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "application/x-www-form-urlencoded");
        headers.set("accept-encoding", "application/gzip");
        headers.set("x-rapidapi-key", translateToken);
        headers.set("x-rapidapi-host", translateHost);
        return headers;
    }
}

