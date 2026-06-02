package com.leandroftm.game_library_api.exception.domain.user_game;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import com.leandroftm.game_library_api.exception.domain.DomainException;

public class GameAlreadyNotFavoriteException extends DomainException {
    public GameAlreadyNotFavoriteException() {
        super("Game is already not favorite", ErrorCode.GAME_ALREADY_NOT_FAVORITE);
    }
}
