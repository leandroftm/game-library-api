package com.leandroftm.game_library_api.integration.igdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class IgdbConfig {

    @Bean
    public RestClient igdbRestClient(
            IgdbProperties properties
    ) {
        return RestClient.builder()
                .baseUrl(properties.getBaseUrl())
                .defaultHeader("Client-ID", properties.getClientId())
                .defaultHeader("Authorization", "Bearer " + properties.getToken())
                .build();
    }
}
