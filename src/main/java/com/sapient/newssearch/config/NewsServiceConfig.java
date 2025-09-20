package com.sapient.newssearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;

import com.sapient.newssearch.constants.NewsSearchConstants;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "news.service.search")
@Validated
@Getter
@Setter
public class NewsServiceConfig {
    @NotBlank
    private String apiKey;

    @NotBlank
    private String url;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(NewsSearchConstants.API_KEY_NAME, apiKey)
                .build();
    }
}
