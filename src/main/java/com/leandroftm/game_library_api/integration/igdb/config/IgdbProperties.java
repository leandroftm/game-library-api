package com.leandroftm.game_library_api.integration.igdb;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "igdb")
public class IgdbProperties {
    private String baseUrl;
    private String clientId;
    private String token;
}
