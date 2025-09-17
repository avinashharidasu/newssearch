package com.sapient.newssearch.dto;

import com.sapient.newssearch.model.NewsArticles;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NewsSearchResponseDto implements Serializable {
    String status;
    int totalArticles;
    List<NewsArticles> articles;
}
