package com.leandroftm.game_library_api.exception.domain.user_game;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import com.leandroftm.game_library_api.exception.domain.NotFoundException;

public class GameNotFoundException extends NotFoundException {
    public GameNotFoundException() {
        super(
                "Game not found for this user", ErrorCode.GAME_NOT_FOUND
        );
    }
}
