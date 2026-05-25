package com.leandroftm.game_library_api.exception.domain.user;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import com.leandroftm.game_library_api.exception.domain.DomainException;

public class UserAlreadyActiveException extends DomainException {
    public UserAlreadyActiveException() {
        super("User already active", ErrorCode.USER_ALREADY_ACTIVE);
    }
}
