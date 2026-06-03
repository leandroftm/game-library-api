package com.leandroftm.game_library_api.integration.igdb.client;

import com.leandroftm.game_library_api.exception.igdb.IgdbGameNotFoundException;
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

    public List<IgdbGameResponse> searchGames(String gameName, int limit, long page) {
        String body = """
                search "%s";
                fields name, platforms.name, first_release_date, genres.name;
                limit %d;
                offset %d;
                """.formatted(gameName, limit,  page);

        List<IgdbGameResponse> games =  restClient.post()
                .uri("/games")
                .body(body)
                .retrieve()
                .body(new ParameterizedTypeReference<List<IgdbGameResponse>>() {
                });

        if (games == null || games.isEmpty()) {
            throw new IgdbGameNotFoundException();
        }

        return games;
    }

    public IgdbGameResponse searchGameById(Long id) {
        String body = """
                        fields name, platforms.name, first_release_date, genres.name;
                        where id = %d;
                """.formatted(id);

        List<IgdbGameResponse> response = restClient.post()
                .uri("/games")
                .body(body)
                .retrieve()
                .body(new ParameterizedTypeReference<List<IgdbGameResponse>>() {
                });

        if (response == null || response.isEmpty()) {
            throw new IgdbGameNotFoundException();
        }

        return response.getFirst();
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
