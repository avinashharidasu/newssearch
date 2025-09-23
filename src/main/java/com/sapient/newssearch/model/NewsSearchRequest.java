package com.sapient.newssearch.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewsSearchRequest {

    @Size(max = 500, message = "Query length cannot be more than 500 chars")
    @Pattern(regexp = "[a-zA-Z'+-]+", message = "Invalid characters found in query string, a-zA-Z'+- are allowed")
    @NotEmpty
    String query;

    @Size(min = 10, max = 19)
    @Pattern(regexp = "^([0-9]{4}-[0-9]{2}-[0-9]{2}|[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2})$")
    String rangeFrom;

    @Size(min = 10, max = 19)
    @Pattern(regexp = "^([0-9]{4}-[0-9]{2}-[0-9]{2}|[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2})$")
    String rangeTo;

    @Pattern(regexp = "^(relevancy|popularity|publishedAt)$", message = "Sort must be 'relevancy' or 'popularity' or 'publishedAt'")
    String sortBy;

    @Min(value = 1)
    @Max(value = 100)
    Integer pageSize;

    @Min(value = 1)
    @Max(value = Integer.MAX_VALUE)
    Integer page;
}
