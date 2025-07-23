package com.autofiller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class LbgClient {
    private final WebClient webClient;

    @Autowired
    public LbgClient(@Qualifier("lbgWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> getLbgDetails(String phoneNumber) {
        String requestBody = String.format("{\"phoneNumber\":\"%s\"}",phoneNumber) ;

        Mono<String> result = webClient.post()
            .uri("/lbgUserDetails")
            .header("accept", "application/json")
            .header("content-type", "application/json")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String.class);

            return result;

            
    }


}
