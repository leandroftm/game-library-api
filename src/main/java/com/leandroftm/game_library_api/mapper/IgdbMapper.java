package com.leandroftm.game_library_api.mapper;

import com.leandroftm.game_library_api.domain.dto.GameSearchResponse;
import com.leandroftm.game_library_api.integration.igdb.dto.IgdbGameResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Component
public class IgdbMapper {

    public GameSearchResponse toResponse(IgdbGameResponse source) {
        return new GameSearchResponse(
                source.id(),
                source.name(),
                mapReleaseDate(source.firstReleaseDate()),
                mapPlatforms(source.platforms()),
                mapGenres(source.genres())
        );
    }

    private LocalDate mapReleaseDate(Long timestamp) {
        if (timestamp == null)
            return null;

        return Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public List<String> mapPlatforms(List<IgdbGameResponse.PlatformResponse> platforms) {
        if (platforms == null)
            return List.of();

        return platforms.stream()
                .map(IgdbGameResponse.PlatformResponse::name)
                .toList();
    }

    private List<String> mapGenres(List<IgdbGameResponse.GenreResponse> genres) {
        if (genres == null) {
            return List.of();
        }
        return genres.stream()
                .map(IgdbGameResponse.GenreResponse::name)
                .toList();

    }
}
