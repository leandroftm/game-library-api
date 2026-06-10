package com.leandroftm.game_library_api.exception.domain.user_game;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import com.leandroftm.game_library_api.exception.domain.DomainException;

public class CompletedGameCannotBeDroppedException extends DomainException {
    public CompletedGameCannotBeDroppedException() {
        super(
                "Completed game cannot be Dropped", ErrorCode.COMPLETED_GAME_CANNOT_BE_DROPPED
        );
    }
}
