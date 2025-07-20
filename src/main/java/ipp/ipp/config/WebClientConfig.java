package ipp.ipp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${ocr.url}")
    private String ocrURL;

    @Bean(name = "ocrWebClient")
    public WebClient ocrWebClient() {
        System.out.println("OCR URL: " + ocrURL);
        return WebClient.builder()
                        .baseUrl(ocrURL)
                        .build();
    }
}


