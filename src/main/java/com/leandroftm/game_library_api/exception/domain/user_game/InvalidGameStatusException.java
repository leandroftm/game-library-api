package com.leandroftm.game_library_api.exception.domain.user_game;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import com.leandroftm.game_library_api.exception.domain.DomainException;

public class InvalidGameStatusException extends DomainException {
    public InvalidGameStatusException() {
        super(
                "Invalid game status", ErrorCode.INVALID_GAME_STATUS
        );
    }
}
