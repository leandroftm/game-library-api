package com.leandroftm.game_library_api.integration.igdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record IgdbGameResponse(
        Long id,
        String name,
        @JsonProperty("first_release_date")
        Long firstReleaseDate,
        List<PlatformResponse> platforms,
        List<GenreResponse> genres
) {
    public record PlatformResponse(String name) { }
    public record GenreResponse(String name) { }
}
