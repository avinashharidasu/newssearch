package com.sapient.newssearch.dto;

import com.sapient.newssearch.model.NewsArticles;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewsSearchResponseDto {
    String status;
    int totalArticles;
    List<NewsArticles> articles;
}
