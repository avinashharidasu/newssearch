package com.sapient.newssearch.mapper;

import com.sapient.newssearch.dto.NewsSearchResponseDto;
import com.sapient.newssearch.model.NewsArticles;
import com.sapient.newssearch.model.NewsSearchResponse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewsSearchResponseMapperImplTest {

    private final NewsSearchResponseMapperImpl mapper = new NewsSearchResponseMapperImpl();

    @Test
    void newsResponseToDto_mapsFieldsAndMakesArticlesUnmodifiable() {
        NewsSearchResponse src = new NewsSearchResponse();
        src.setStatus("ok");
        src.setTotalResults(3);
        List<NewsArticles> articles = new ArrayList<>();
        articles.add(new NewsArticles());
        src.setArticles(articles);

        var dto = mapper.newsResponseToDto(src);
        assertEquals("ok", dto.getStatus());
        assertEquals(3, dto.getTotalArticles());
        assertNotNull(dto.getArticles());
        assertThrows(UnsupportedOperationException.class, () -> dto.getArticles().add(new NewsArticles()));
    }

    @Test
    void newsResponseToDto_overload_handlesNullAndWrongType() {
        assertNull(mapper.newsResponseToDto("not a response"));
        assertNull(mapper.newsResponseToDto((Object) null));
    }

    @Test
    void dtoToNewsResponse_mapsBackwardsAndHandlesNullArticles() {
        NewsSearchResponseDto dto = new NewsSearchResponseDto();
        dto.setStatus("ok");
        dto.setTotalArticles(5);
        dto.setArticles(null);

        var result = mapper.dtoToNewsResponse(dto);
        assertEquals("ok", result.getStatus());
        assertEquals(5, result.getTotalResults());
        assertNotNull(result.getArticles());
        assertTrue(result.getArticles().isEmpty());
    }
}


