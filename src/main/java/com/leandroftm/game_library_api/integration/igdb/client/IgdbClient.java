package com.leandroftm.game_library_api.integration.igdb.client;

import com.leandroftm.game_library_api.integration.igdb.dto.IgdbGameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IgdbClient {
    private final RestClient restClient;

    public List<IgdbGameResponse> searchGames(String gameName) {
        String body = """
                search "%s;
                fields name, platforms.name;
                limit 10;
                """.formatted(gameName);

        return restClient.post()
                .uri("/games")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public String searchGame(String gameName) {

        String body = """
                search "%s";
                fields name;
                limit 10;
                """.formatted(gameName);

        return restClient.post()
                .uri("/games")
                .body(body)
                .retrieve()
                .body(String.class);
    }
}
