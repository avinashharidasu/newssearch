package com.sapient.newssearch.security;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenAPISecurityConfigTest {

    @Test
    void customOpenAPI_definesBearerAuthScheme() {
        OpenAPISecurityConfig config = new OpenAPISecurityConfig();
        OpenAPI openAPI = config.customOpenAPI();
        assertNotNull(openAPI);
        assertTrue(openAPI.getComponents().getSecuritySchemes().containsKey("bearerAuth"));
        assertFalse(openAPI.getSecurity().isEmpty());
    }
}


