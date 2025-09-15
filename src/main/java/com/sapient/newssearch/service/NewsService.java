package com.sapient.newssearch.service;

import org.springframework.stereotype.Service;

import com.sapient.newssearch.client.NewsClient;
import com.sapient.newssearch.dto.NewsSearchResponseDto;
import com.sapient.newssearch.mapper.NewsSearchResponseMapper;
import com.sapient.newssearch.model.NewsSearchRequest;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsService {

    private static final String NEWS_RESULT_RETRY = "newsSearchRetryPolicy";
    private final NewsClient client;
    private final NewsSearchResponseMapper mapper;

    @Retry(name = NEWS_RESULT_RETRY, fallbackMethod = "defaultNewsResults")
    public Mono<NewsSearchResponseDto> getNewsResults(NewsSearchRequest request) {
        var response = client.getNewsResults(request);
        return response.map(mapper::newsResponseToDto);
    }

    public Mono<NewsSearchResponseDto> defaultNewsResults(NewsSearchRequest request, Exception ex) {
        log.error("Retry policy newsSearchRetryPolicy triggered due to exception {}", ex.getMessage());
        var fallback = new NewsSearchResponseDto();
        fallback.setStatus("fallback");
        fallback.setTotalArticles(0);
        fallback.setArticles(java.util.Collections.emptyList());
        return Mono.just(fallback);
    }
}
