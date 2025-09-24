package com.sapient.newssearch.config;

import com.sapient.newssearch.dto.NewsSearchResponseDto;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

import static com.sapient.newssearch.constants.NewsSearchConstants.NEWS_RESULT_CACHE;

@Configuration
public class NewsCacheConfig {
    @Bean
    public ReactiveRedisTemplate<String, NewsSearchResponseDto> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();

        RedisSerializer<NewsSearchResponseDto> valueSerializer = new Jackson2JsonRedisSerializer<>(NewsSearchResponseDto.class);


        return new ReactiveRedisTemplate<>(connectionFactory,
                RedisSerializationContext.<String, NewsSearchResponseDto>newSerializationContext()
                        .key(keySerializer)
                        .value(valueSerializer)
                        .hashKey(keySerializer)
                        .hashValue(valueSerializer)
                        .build());
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(60))
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration(NEWS_RESULT_CACHE,
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(60))); // Specific TTL for a named cache
    }

}