package com.sapient.newssearch.controller;

import com.sapient.newssearch.dto.NewsSearchResponseDto;
import com.sapient.newssearch.model.NewsSearchRequest;
import com.sapient.newssearch.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController("/v1/api")
@Tag(name = "News Search")
public class NewsSearchController {

    private final NewsService service;

    @GetMapping(value = "/news/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get News articles",
            description = "API to fetch News article details based on search criteria",
            responses = {
                    @ApiResponse(description = "Successful response from News Service", responseCode = "200"),
                    @ApiResponse(description = "Invalid input request", responseCode = "400"),
                    @ApiResponse(description = "Failed to process request", responseCode = "500")
            }
    )
    Mono<NewsSearchResponseDto> getNewsResults(@Valid NewsSearchRequest request) {
        service.getNewsResults(request).log();
        return service.getNewsResults(request);
    }

}
