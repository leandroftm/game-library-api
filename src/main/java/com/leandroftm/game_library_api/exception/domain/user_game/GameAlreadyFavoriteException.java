package com.leandroftm.game_library_api.exception.domain.user_game;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import com.leandroftm.game_library_api.exception.domain.DomainException;

public class GameAlreadyFavoriteException extends DomainException {
    public GameAlreadyFavoriteException() {
        super("Game is already favorite", ErrorCode.GAME_ALREADY_FAVORITE);
    }
}
