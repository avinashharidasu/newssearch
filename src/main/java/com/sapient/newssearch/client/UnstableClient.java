package com.sapient.newssearch.client;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UnstableClient {
    private static final String UNSTABLE_SERVICE = "unstableService";
    private final WebClient webClient;

    public UnstableClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    public Mono<String> unstable() {
        return webClient.get()
                .uri("/unstable")
                .retrieve()
                .bodyToMono(String.class);
    }

    @Retry(name = UNSTABLE_SERVICE, fallbackMethod = "defaultProduct")
    public Mono<String> unstableWithRetry() {
        log.info("Started to call service");
        return webClient.get()
                .uri("/unstable")
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> defaultProduct(Exception ex) {
        log.error("in retry default product method");
        return Mono.just("DVD");
    }
}