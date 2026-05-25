package com.leandroftm.game_library_api.exception.domain.user_game;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import com.leandroftm.game_library_api.exception.domain.DomainException;

public class GameStatusAlreadyPlayingException extends DomainException {
    public GameStatusAlreadyPlayingException() {
        super("Game status is already playing", ErrorCode.GAME_STATUS_ALREADY_PLAYING);
    }
}
