package com.sapient.newssearch.service;

import com.sapient.newssearch.model.NewsSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

@Slf4j
@Component
public class NewsCacheKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {

        if (params[0] instanceof NewsSearchRequest request) {
            log.info("Started to construct cache key {}", target.getClass().getSimpleName() + "_"
                    + method.getName() + "_"
                    + request.getQuery() + "_"
                    + request.getRangeFrom());
            return target.getClass().getSimpleName() + "_"
                    + method.getName() + "_"
                    + request.getQuery() + "_"
                    + request.getRangeFrom();
        }

        return target.getClass().getSimpleName() + "_"
                + method.getName() + "_"
                + StringUtils.arrayToDelimitedString(params, "_");
    }
}