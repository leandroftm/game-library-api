package com.leandroftm.game_library_api.domain.dto.response;

import java.time.LocalDate;
import java.util.List;

public record GameSearchResponse (
        Long id,
        String name,
        LocalDate releaseDate,
        List<String> platforms,
        List<String> genres
){
}
