package com.leandroftm.game_library_api.exception.domain.user_game;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import com.leandroftm.game_library_api.exception.domain.DomainException;

public class GameStatusIsNotPlayingException extends DomainException {
    public GameStatusIsNotPlayingException() {
        super("Only playing games can be completed", ErrorCode.GAME_STATUS_IS_NOT_PLAYING);
    }
}
