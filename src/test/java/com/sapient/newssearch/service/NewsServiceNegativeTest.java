package com.sapient.newssearch.service;

import com.sapient.newssearch.client.NewsClient;
import com.sapient.newssearch.dto.NewsSearchResponseDto;
import com.sapient.newssearch.mapper.NewsSearchResponseMapper;
import com.sapient.newssearch.model.NewsSearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewsServiceNegativeTest {

    private NewsClient client;
    private NewsSearchResponseMapper mapper;
    private ReactiveRedisTemplate<String, NewsSearchResponseDto> template;
    private NewsService service;

    @BeforeEach
    void setUp() {
        client = mock(NewsClient.class);
        mapper = mock(NewsSearchResponseMapper.class);
        template = mock(ReactiveRedisTemplate.class);
        service = new NewsService(client, mapper, template);
    }

    @Test
    void defaultNewsResults_whenKeysNull_returnsFallback() {
        NewsSearchRequest request = new NewsSearchRequest();
        request.setQuery("ai");

        when(template.keys(anyString())).thenReturn(null);

        var dto = service.defaultNewsResults(request, new RuntimeException("boom")).block();
        assertNotNull(dto);
        assertEquals("No results found", dto.getStatus());
        assertEquals(0, dto.getTotalArticles());
    }

    @Test
    void defaultNewsResults_whenFirstKeyEmpty_returnsFallback() {
        NewsSearchRequest request = new NewsSearchRequest();
        request.setQuery("ai");

        when(template.keys(anyString())).thenReturn(Flux.<String>empty());

        var dto = service.defaultNewsResults(request, new RuntimeException("boom")).block();
        assertNotNull(dto);
        assertEquals("No results found", dto.getStatus());
    }

    @Test
    void defaultNewsResults_whenCacheMiss_returnsFallback() {
        NewsSearchRequest request = new NewsSearchRequest();
        request.setQuery("ai");

        when(template.keys(anyString())).thenReturn(Flux.just("KEY1"));

        ReactiveValueOperations<String, NewsSearchResponseDto> ops = mock(ReactiveValueOperations.class);
        when(template.opsForValue()).thenReturn(ops);
        when(ops.get("KEY1")).thenReturn(Mono.empty());

        var dto = service.defaultNewsResults(request, new RuntimeException("boom")).block();
        assertNotNull(dto);
        assertEquals("No results found", dto.getStatus());
    }
}


