package com.leandroftm.game_library_api.exception.domain.user_game;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import com.leandroftm.game_library_api.exception.domain.DataIntegrityViolationException;

public class GameAlreadyExistsException extends DataIntegrityViolationException {
    public GameAlreadyExistsException() {
        super(
                "Game already exists", ErrorCode.GAME_ALREADY_EXISTS
        );
    }
}
