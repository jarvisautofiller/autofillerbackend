package com.autofiller.config;

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
        return WebClient.builder()
                        .baseUrl(ocrURL)
                        .build();
    }

    @Value("${gov.url}")
    private String govURL;

    @Bean(name = "govWebClient")
    public WebClient govWebClient() {     
        return WebClient.builder()
                        .baseUrl(govURL)
                        .build();
    }

    @Value("${lbg.url}")
    private String lbgURL;

    @Bean(name = "lbgWebClient")
    public WebClient lbgClient() {     
        return WebClient.builder()
                        .baseUrl(lbgURL)
                        .build();
    }

    @Value("${validateservice.url}")
    private String validateServiceUrl;

    @Bean(name = "vserviceWebClient")
    public WebClient validateServiceCleint() {     
        return WebClient.builder()
                        .baseUrl(validateServiceUrl)
                        .build();
    }
}


