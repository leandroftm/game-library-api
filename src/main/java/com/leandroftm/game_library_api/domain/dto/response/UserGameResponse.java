package com.leandroftm.game_library_api.domain.dto.response;

import com.leandroftm.game_library_api.domain.entity.UserGame;
import com.leandroftm.game_library_api.domain.enums.GameStatus;

import java.time.LocalDateTime;

public record UserGameResponse(
        Long id,
        Long igdbId,
        String gameName,
        boolean favorite,
        LocalDateTime startDate,
        LocalDateTime completeDate,
        GameStatus status
) {
    public UserGameResponse(UserGame userGame){
        this(
                userGame.getId(),
                userGame.getIgdbId(),
                userGame.getGameName(),
                userGame.isFavorite(),
                userGame.getStartDate(),
                userGame.getCompletedDate(),
                userGame.getStatus()
        );
    }
}
