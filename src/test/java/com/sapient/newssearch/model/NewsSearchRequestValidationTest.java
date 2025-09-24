package com.sapient.newssearch.model;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class NewsSearchRequestValidationTest {

    private static Validator validator;

    @BeforeAll
    static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validRequest_passesAllConstraints() {
        NewsSearchRequest req = new NewsSearchRequest();
        req.setQuery("Tesla");
        req.setRangeFrom("2024-01-01");
        req.setRangeTo("2024-12-31T23:59:59");
        req.setSortBy("relevancy");
        req.setPageSize(20);
        req.setPage(1);

        Set<ConstraintViolation<NewsSearchRequest>> violations = validator.validate(req);
        assertTrue(violations.isEmpty(), () -> "Unexpected violations: " + violations);
    }

    @Test
    void query_invalidCharacters_failsPattern() {
        NewsSearchRequest req = new NewsSearchRequest();
        req.setQuery("tesla!!!");

        Set<ConstraintViolation<NewsSearchRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("query")));
    }

    @Test
    void query_empty_failsNotEmpty() {
        NewsSearchRequest req = new NewsSearchRequest();
        req.setQuery("");

        Set<ConstraintViolation<NewsSearchRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("query")));
    }

    @Test
    void query_tooLong_failsSize() {
        NewsSearchRequest req = new NewsSearchRequest();
        req.setQuery("a".repeat(501));

        Set<ConstraintViolation<NewsSearchRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("query")));
    }

    @Test
    void dateFields_invalidSizeOrPattern_failValidation() {
        NewsSearchRequest req = new NewsSearchRequest();
        req.setQuery("News");
        req.setRangeFrom("2024/01/01");
        req.setRangeTo("20240101");

        Set<ConstraintViolation<NewsSearchRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("rangeFrom")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("rangeTo")));
    }

    @Test
    void sortBy_invalidValue_failsPattern() {
        NewsSearchRequest req = new NewsSearchRequest();
        req.setQuery("AI");
        req.setSortBy("ascending");

        Set<ConstraintViolation<NewsSearchRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("sortBy")));
    }

    @Test
    void pageSize_outOfRange_failsMinMax() {
        NewsSearchRequest tooSmall = new NewsSearchRequest();
        tooSmall.setQuery("AI");
        tooSmall.setPageSize(0);

        Set<ConstraintViolation<NewsSearchRequest>> v1 = validator.validate(tooSmall);
        assertFalse(v1.isEmpty());
        assertTrue(v1.stream().anyMatch(v -> v.getPropertyPath().toString().equals("pageSize")));

        NewsSearchRequest tooLarge = new NewsSearchRequest();
        tooLarge.setQuery("AI");
        tooLarge.setPageSize(101);

        Set<ConstraintViolation<NewsSearchRequest>> v2 = validator.validate(tooLarge);
        assertFalse(v2.isEmpty());
        assertTrue(v2.stream().anyMatch(v -> v.getPropertyPath().toString().equals("pageSize")));
    }

    @Test
    void page_outOfRange_failsMin() {
        NewsSearchRequest req = new NewsSearchRequest();
        req.setQuery("AI");
        req.setPage(0);

        Set<ConstraintViolation<NewsSearchRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("page")));
    }
}


