package com.leandroftm.game_library_api.controller;

import com.leandroftm.game_library_api.domain.dto.response.GameSearchResponse;
import com.leandroftm.game_library_api.service.IgdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {

    private final IgdbService igdbService;

    @GetMapping("/{id}")
    public ResponseEntity<GameSearchResponse> searchGameById(@PathVariable long id) {
        return ResponseEntity.ok(igdbService.searchGameById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GameSearchResponse>> searchGame(@RequestParam String name, @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        Page<GameSearchResponse> page = igdbService.searchGamesByName(name, pageable);

        return ResponseEntity.ok(page);
    }
}
