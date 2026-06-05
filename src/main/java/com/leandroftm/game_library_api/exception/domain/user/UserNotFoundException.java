package com.leandroftm.game_library_api.exception.domain.user;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import com.leandroftm.game_library_api.exception.domain.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super(
                "User not found", ErrorCode.USER_NOT_FOUND
        );
    }
}
