package com.leandroftm.game_library_api.integration.igdb.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "igdb")
@Getter
@Setter
public class IgdbProperties {
    private String baseUrl;
    private String clientId;
    private String token;
}
