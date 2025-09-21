package com.sapient.newssearch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
@Profile("test")
public class NewsSecurityConfigTest {

    @Bean
    public SecurityWebFilterChain testFilterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(authorize -> authorize
                        .anyExchange().permitAll()
                )
                .csrf(csrf -> csrf.disable());
        
        return http.build();
    }
}
