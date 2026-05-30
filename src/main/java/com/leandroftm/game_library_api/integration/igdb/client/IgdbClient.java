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
                search "%s";
                fields name, platforms.name, first_release_date, genres.name;
                limit 10;
                """.formatted(gameName);

        return restClient.post()
                .uri("/games")
                .body(body)
                .retrieve()
                .body(new ParameterizedTypeReference<List<IgdbGameResponse>>() {
                });
    }

    public String searchGame(String gameName) {

        String body = """
                search "%s";
                fields name, platforms.name, first_release_date, genres.name;
                limit 10;
                """.formatted(gameName);

        return restClient.post()
                .uri("/games")
                .body(body)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
        });
    }
}
