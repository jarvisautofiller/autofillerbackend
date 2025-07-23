package com.autofiller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.autofiller.model.OtpResponse;
import reactor.core.publisher.Mono;

@Component
public class ValidatorClient {


     private final WebClient webClient;

    @Autowired
    public ValidatorClient(@Qualifier("vserviceWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        String requestBody = String.format("{\"mobile\":\"%s\"}",phoneNumber) ;


        OtpResponse result = webClient.post()
        .uri("/verifyService/initiate-call")
        .header("accept", "application/json")
        .header("content-type", "application/json")
        .bodyValue(requestBody)
        .retrieve()
        .bodyToMono(OtpResponse.class)
        .block();
        return "validated".equalsIgnoreCase(result.getStatus());

            
    }
}
