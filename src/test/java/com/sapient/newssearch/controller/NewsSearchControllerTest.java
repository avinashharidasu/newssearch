package com.sapient.newssearch.controller;

import com.sapient.newssearch.dto.NewsSearchResponseDto;
import com.sapient.newssearch.service.NewsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class NewsSearchControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private NewsService newsService;

    @Test
    void getNewsResults_success() {
        var dto = new NewsSearchResponseDto();
        dto.setStatus("ok");
        dto.setTotalArticles(2);

        when(newsService.getNewsResults(any())).thenReturn(Mono.just(dto));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v1/api/news/search")
                        .queryParam("query", "tesla")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("ok")
                .jsonPath("$.totalArticles").isEqualTo(2);
    }

    @Test
    void getNewsResults_validationFailure() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v1/api/news/search")
                        .queryParam("query", "!!!")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError();
    }
}


