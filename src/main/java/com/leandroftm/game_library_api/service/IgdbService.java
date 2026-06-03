package com.leandroftm.game_library_api.service;

import com.leandroftm.game_library_api.domain.dto.GameSearchResponse;
import com.leandroftm.game_library_api.integration.igdb.client.IgdbClient;
import com.leandroftm.game_library_api.integration.igdb.dto.IgdbGameResponse;
import com.leandroftm.game_library_api.mapper.IgdbMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IgdbService {

    private final IgdbMapper mapper;

    private final IgdbClient client;

    public GameSearchResponse searchGameById(Long id) {
        return mapper.toResponse(client.searchGameById(id));
    }

    public Page<GameSearchResponse> searchGamesByName(String name, Pageable pageable) {
        List<IgdbGameResponse> igdbResponse = client.searchGames(name, pageable.getPageSize(), pageable.getOffset());

        List<GameSearchResponse> games = igdbResponse.stream().map(mapper::toResponse).toList();
        return toPage(games, pageable);
    }

    private Page<GameSearchResponse> toPage(List<GameSearchResponse> response, Pageable pageable) {
        return new PageImpl<>(response, pageable, response.size());
    }
}
