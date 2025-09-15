package com.sapient.newssearch.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewsSearchResponse {
     String status;
    private int totalResults;
    private List<NewsArticles> articles;
}
