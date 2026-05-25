package com.leandroftm.game_library_api.exception.domain.user_game;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import com.leandroftm.game_library_api.exception.domain.DomainException;

public class GameStatusAlreadyDroppedException extends DomainException {
    public GameStatusAlreadyDroppedException() {
        super(
                "Game status is already dropped", ErrorCode.GAME_STATUS_ALREADY_DROPPED
        );
    }
}
