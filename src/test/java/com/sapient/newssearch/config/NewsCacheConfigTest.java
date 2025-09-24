package com.sapient.newssearch.config;

import com.sapient.newssearch.dto.NewsSearchResponseDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import static org.junit.jupiter.api.Assertions.*;

class NewsCacheConfigTest {

    @Test
    void reactiveRedisTemplate_createsTemplate() {
        NewsCacheConfig config = new NewsCacheConfig();
        ReactiveRedisConnectionFactory factory = Mockito.mock(ReactiveRedisConnectionFactory.class);
        ReactiveRedisTemplate<String, NewsSearchResponseDto> template = config.reactiveRedisTemplate(factory);
        assertNotNull(template);
    }

    @Test
    void cacheConfiguration_setsSerializationAndTtl() {
        NewsCacheConfig config = new NewsCacheConfig();
        var cacheConfig = config.cacheConfiguration();
        assertNotNull(cacheConfig);
        RedisSerializationContext.SerializationPair<?> keyPair = cacheConfig.getKeySerializationPair();
        RedisSerializationContext.SerializationPair<?> valuePair = cacheConfig.getValueSerializationPair();
        assertNotNull(keyPair);
        assertNotNull(valuePair);
    }
}


