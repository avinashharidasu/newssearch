package com.sapient.newssearch.service;

import com.sapient.newssearch.model.NewsSearchRequest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class NewsCacheKeyGeneratorTest {

    static class DummyTarget { public void someMethod() {} public void another(Object a, Object b) {} }

    @Test
    void generate_withNewsSearchRequest_buildsUppercaseKey() throws Exception {
        NewsCacheKeyGenerator generator = new NewsCacheKeyGenerator();
        DummyTarget target = new DummyTarget();
        Method method = DummyTarget.class.getMethod("someMethod");

        NewsSearchRequest request = new NewsSearchRequest();
        request.setQuery("tesla");
        request.setRangeFrom("2024-01-01");

        Object key = generator.generate(target, method, request);
        String s = key.toString();
        assertTrue(s.startsWith("DUMMYTARGET_SOMEMETHOD_TESLA_2024-01-01"));
        assertEquals(s, s.toUpperCase());
    }

    @Test
    void generate_withGenericParams_joinsParamsUppercase() throws Exception {
        NewsCacheKeyGenerator generator = new NewsCacheKeyGenerator();
        DummyTarget target = new DummyTarget();
        Method method = DummyTarget.class.getMethod("another", Object.class, Object.class);

        Object key = generator.generate(target, method, "a", 2);
        String s = key.toString();
        assertTrue(s.startsWith("DUMMYTARGET_ANOTHER_A_2"));
        assertEquals(s, s.toUpperCase());
    }
}


