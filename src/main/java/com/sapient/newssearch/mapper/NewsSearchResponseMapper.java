package com.sapient.newssearch.mapper;

import com.sapient.newssearch.dto.NewsSearchResponseDto;
import com.sapient.newssearch.model.NewsSearchResponse;
import org.springframework.stereotype.Component;

@Component
public interface NewsSearchResponseMapper {
    NewsSearchResponseDto newsResponseToDto(NewsSearchResponse source);

    NewsSearchResponse dtoToNewsResponse(NewsSearchResponseDto destination);
}
