package com.sapient.newssearch.config;

import com.sapient.newssearch.model.NewsSearchRequest;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

@Component
public class NewsCacheKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {

        if (params[0] instanceof NewsSearchRequest request) {
            return target.getClass().getSimpleName() + "_"
                    + method.getName() + "_"
                    + request.getQuery() +
                    request.getRangeFrom();
        }

        return target.getClass().getSimpleName() + "_"
                + method.getName() + "_"
                + StringUtils.arrayToDelimitedString(params, "_");
    }
}