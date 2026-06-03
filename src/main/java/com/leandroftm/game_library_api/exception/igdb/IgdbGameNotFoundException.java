package com.leandroftm.game_library_api.exception.igdb;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import com.leandroftm.game_library_api.exception.domain.NotFoundException;

public class IgdbGameNotFoundException extends NotFoundException {
    public IgdbGameNotFoundException() {
        super("Game not found on API", ErrorCode.IGDB_GAME_NOT_FOUND);
    }
}
