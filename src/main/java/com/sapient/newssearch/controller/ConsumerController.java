package com.sapient.newssearch.controller;

import com.sapient.newssearch.client.UnstableClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ConsumerController {
    private final UnstableClient unstableClient;
    public ConsumerController(UnstableClient unstableClient) {
        this.unstableClient = unstableClient;
    }
    @GetMapping("/unstable-client")
    public Mono<String> unstable() {
        return unstableClient.unstable();
    }
    @GetMapping("/unstable-with-retry-client")
    public Mono<String> unstableWithRetry() {
        return unstableClient.unstableWithRetry();
    }
}
