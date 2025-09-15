package com.sapient.newssearch.mapper;

import com.sapient.newssearch.dto.NewsSearchResponseDto;
import com.sapient.newssearch.model.NewsSearchResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class NewsSearchResponseMapperImpl implements NewsSearchResponseMapper {
    @Override
    public NewsSearchResponseDto newsResponseToDto(NewsSearchResponse source) {
        final var newsSearchResponseDto = new NewsSearchResponseDto();
        newsSearchResponseDto.setTotalArticles(source.getTotalResults());
        newsSearchResponseDto.setStatus(source.getStatus());
        newsSearchResponseDto.setArticles(Collections.unmodifiableList(source.getArticles()));

        return newsSearchResponseDto;
    }

    @Override
    public NewsSearchResponse dtoToNewsResponse(NewsSearchResponseDto destination) {
        return null;
    }
}
