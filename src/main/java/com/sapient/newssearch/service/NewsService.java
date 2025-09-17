package com.sapient.newssearch.service;

import com.sapient.newssearch.client.NewsClient;
import com.sapient.newssearch.dto.NewsSearchResponseDto;
import com.sapient.newssearch.mapper.NewsSearchResponseMapper;
import com.sapient.newssearch.model.NewsSearchRequest;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.sapient.newssearch.constants.NewsSearchConstants.NEWS_RESULT_RETRY;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsService {

    private final NewsClient client;
    private final NewsSearchResponseMapper mapper;

    @Retry(name = NEWS_RESULT_RETRY, fallbackMethod = "defaultNewsResults")
    @Cacheable(value = "NEWS_RESULT_CACHE", keyGenerator = "newsCacheKeyGenerator")
    public Mono<NewsSearchResponseDto> getNewsResults(NewsSearchRequest request) {
        log.debug("News article search request initiated with {}", request);

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
