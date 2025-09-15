package com.sapient.newssearch.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsArticles {
    String author;
    String title;
    String description;
    String url;
    String urlToImage;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    LocalDateTime publishedAt;

    String content;
}
