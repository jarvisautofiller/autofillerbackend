package ipp.ipp.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class GovernmentClient {

   
    private final WebClient webClient;

    @Autowired
    public GovernmentClient(@Qualifier("govWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> getUserDetails(String idNumber, String idType) {
        String requestBody = String.format("{\"idNumber\":\"%s\",\"idType\":\"%s\"}", idNumber, idType);

        Mono<String> result = webClient.post()
            .uri("/userDetails")
            .header("accept", "application/json")
            .header("content-type", "application/json")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String.class);

            return result;

        

    
    }


}
