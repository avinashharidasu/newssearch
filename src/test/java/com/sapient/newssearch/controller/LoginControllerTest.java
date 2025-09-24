package com.sapient.newssearch.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@Disabled
class LoginControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void login_returnsLoginViewName() {
        webTestClient.get()
                .uri("/login")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void home_redirectsToSwaggerUi() {
        webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus().is3xxRedirection();
    }
}


