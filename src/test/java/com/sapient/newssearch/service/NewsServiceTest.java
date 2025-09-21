package com.sapient.newssearch.service;

import com.sapient.newssearch.client.NewsClient;
import com.sapient.newssearch.dto.NewsSearchResponseDto;
import com.sapient.newssearch.mapper.NewsSearchResponseMapper;
import com.sapient.newssearch.model.NewsSearchRequest;
import com.sapient.newssearch.model.NewsSearchResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class NewsServiceTest {

    private NewsClient client;
    private NewsSearchResponseMapper mapper;
    private NewsService service;

    @BeforeEach
    void setUp() {
        client = mock(NewsClient.class);
        mapper = mock(NewsSearchResponseMapper.class);
        service = new NewsService(client, mapper);
    }

    @Test
    void getNewsResults_mapsResponse() {
        var request = new NewsSearchRequest();
        request.setQuery("tesla");

        var clientResponse = new NewsSearchResponse();
        clientResponse.setStatus("ok");
        clientResponse.setTotalResults(1);

        var dto = new NewsSearchResponseDto();
        dto.setStatus("ok");
        dto.setTotalArticles(1);

        when(client.getNewsResults(request)).thenReturn(Mono.just(clientResponse));
        when(mapper.newsResponseToDto(clientResponse)).thenReturn(dto);

        var result = service.getNewsResults(request).block();
        assertNotNull(result);
        assertEquals("ok", result.getStatus());
        assertEquals(1, result.getTotalArticles());

        verify(client, times(1)).getNewsResults(request);
        verify(mapper, times(1)).newsResponseToDto(clientResponse);
    }

    @Test
    void defaultNewsResults_returnsFallback() {
        var request = new NewsSearchRequest();
        var result = service.defaultNewsResults(request, new RuntimeException("Response from fallback")).block();
        assertNotNull(result);
        assertEquals("fallback", result.getStatus());
        assertEquals(0, result.getTotalArticles());
    }
}


