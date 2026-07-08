package com.leandroftm.game_library_api.security.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth"; // scheme name
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme() //name, new security scheme
                                .type(SecurityScheme.Type.HTTP) //scheme type
                                .scheme("bearer") // authentication scheme
                                .bearerFormat("JWT"))); // token format
    }
}
