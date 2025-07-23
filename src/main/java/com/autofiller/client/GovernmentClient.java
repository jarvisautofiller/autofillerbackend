package com.autofiller.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GovernmentClient {

    private final WebClient webClient;

    @Autowired
    public GovernmentClient(@Qualifier("govWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> getUserDetails(String idNumber, String idType) {
        String requestBody = String.format("{\"idNumber\":\"%s\",\"idType\":\"%s\"}", idNumber, idType);

        return webClient.post()
                .uri("/userDetails")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return response.createException();
                    }
                    return Mono.error(new RuntimeException("User not found")); // ← this returns WebClientResponseException
                })
                
                .bodyToMono(String.class);

    }

}
