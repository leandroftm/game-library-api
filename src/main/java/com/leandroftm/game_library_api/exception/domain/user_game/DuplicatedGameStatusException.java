package com.leandroftm.game_library_api.exception.domain.user_game;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import com.leandroftm.game_library_api.exception.domain.DataIntegrityViolationException;

public class DuplicatedGameStatusException extends DataIntegrityViolationException {
    public DuplicatedGameStatusException() {
        super(
                "The game status is duplicated", ErrorCode.DUPLICATED_GAME_STATUS
        );
    }
}
