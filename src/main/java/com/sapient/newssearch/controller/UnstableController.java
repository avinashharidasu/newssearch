package com.sapient.newssearch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Random;

@RestController("/v2/api")
public class UnstableController {
    public static final int BOUND = 5;

    @GetMapping("/unstable")
    public Mono<String> unstable() throws Exception {
        if (new Random().nextInt(BOUND) == 1) {
            throw new Exception("oups something bad has happend");
        }
        return Mono.just("asdf");
    }
}