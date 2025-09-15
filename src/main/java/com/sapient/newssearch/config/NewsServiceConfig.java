package com.sapient.newssearch.config;

import com.sapient.newssearch.constants.NewsSearchConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ConfigurationProperties(prefix = "news.service.search")
@Getter
@Setter
public class NewsServiceConfig {
    String apiKey;
    String URL;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl(URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(NewsSearchConstants.API_KEY_NAME, apiKey)
                .build();
    }
}
