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
    public NewsSearchResponseDto newsResponseToDto(Object source) {
        if (source instanceof NewsSearchResponse response) {
            return newsResponseToDto(response);
        } else {
            return null;
        }
    }

    @Override
    public NewsSearchResponse dtoToNewsResponse(NewsSearchResponseDto destination) {
        final var response = new NewsSearchResponse();
        response.setStatus(destination.getStatus());
        response.setTotalResults(destination.getTotalArticles());
        response.setArticles(destination.getArticles() == null ? Collections.emptyList() : destination.getArticles());
        return response;
    }
}
