package com.leandroftm.game_library_api.domain.dto.request;

import com.leandroftm.game_library_api.domain.enums.GameStatus;

public record UpdateGameStatusRequest(
        GameStatus status
) {
}
