package com.sapient.newssearch.client;

import com.sapient.newssearch.model.NewsSearchRequest;
import com.sapient.newssearch.model.NewsSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;

import static com.sapient.newssearch.constants.NewsSearchConstants.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewsClient {

    private final WebClient client;

    private static Function<UriBuilder, URI> getNewsURI(NewsSearchRequest request) {
        return uribuilder -> uribuilder
                .path(EVERYTHING)
                .queryParam(QUERY, request.getQuery())
                .queryParam(RANGE_FROM, request.getRangeFrom())
                .queryParam(RANGE_TO, request.getRangeTo())
                .queryParam(SORT_BY, request.getSortBy())
                .queryParam(PAGE, request.getPage())
                .queryParam(PAGE_SIZE, request.getPageSize())
                .queryParam(LANGUAGE_KEY, LANGUAGE_VALUE)
                .build();
    }

    public Mono<NewsSearchResponse> getNewsResults(NewsSearchRequest request) {
        log.debug("Received request to search news for request {}", request);

        return client.get()
                .uri(getNewsURI(request))
                .retrieve()
                .bodyToMono(NewsSearchResponse.class);
    }

}
