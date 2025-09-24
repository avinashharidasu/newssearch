package com.sapient.newssearch.client;

import com.sapient.newssearch.model.NewsSearchRequest;
import com.sapient.newssearch.model.NewsSearchResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class NewsClientTest {

    private static class CapturingExchangeFunction implements ExchangeFunction {
        ClientRequest lastRequest;

        @Override
        public Mono<ClientResponse> exchange(ClientRequest request) {
            this.lastRequest = request;
            return Mono.just(ClientResponse.create(org.springframework.http.HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body("{\"status\":\"ok\",\"totalResults\":0,\"articles\":[]}")
                    .build());
        }
    }

    private CapturingExchangeFunction exchangeFunction;
    private WebClient webClient;
    private NewsClient newsClient;

    @BeforeEach
    public void setUp() {
        exchangeFunction = new CapturingExchangeFunction();
        webClient = WebClient.builder().baseUrl("https://example.com").exchangeFunction(exchangeFunction).build();
        newsClient = new NewsClient(webClient);
    }

    @Test
    void getNewsResults_buildsExpectedUriAndMethod() {
        NewsSearchRequest req = new NewsSearchRequest();
        req.setQuery("tesla");
        req.setRangeFrom("2024-01-01");
        req.setRangeTo("2024-12-31");
        req.setSortBy("publishedAt");
        req.setPage(2);
        req.setPageSize(50);

        NewsSearchResponse response = newsClient.getNewsResults(req).block();
        assertNotNull(response);

        ClientRequest captured = exchangeFunction.lastRequest;
        assertNotNull(captured);
        assertEquals("GET", captured.method().name());
        URI uri = captured.url();
        assertEquals("https", uri.getScheme());
        assertTrue(uri.getPath().endsWith("/everything"));
        String query = uri.getQuery();
        assertTrue(query.contains("q=tesla"));
        assertTrue(query.contains("from=2024-01-01"));
        assertTrue(query.contains("to=2024-12-31"));
        assertTrue(query.contains("sortBy=publishedAt"));
        assertTrue(query.contains("page=2"));
        assertTrue(query.contains("pageSize=50"));
        assertTrue(query.contains("language=en"));
    }
}


