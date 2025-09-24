package com.sapient.newssearch.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class NewsSearchControllerValidationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void invalid_sortBy_param_returns4xx() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v1/api/news/search")
                        .queryParam("query", "AI")
                        .queryParam("sortBy", "ascending")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void invalid_date_params_return4xx() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v1/api/news/search")
                        .queryParam("query", "AI")
                        .queryParam("rangeFrom", "2024/01/01")
                        .queryParam("rangeTo", "2024-13-01")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void invalid_pagination_params_return4xx() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v1/api/news/search")
                        .queryParam("query", "AI")
                        .queryParam("page", "0")
                        .queryParam("pageSize", "101")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError();
    }
}


