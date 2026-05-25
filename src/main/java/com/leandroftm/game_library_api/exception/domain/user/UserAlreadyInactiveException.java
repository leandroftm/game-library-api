package com.leandroftm.game_library_api.exception.domain.user;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import com.leandroftm.game_library_api.exception.domain.DomainException;

public class UserAlreadyInactiveException extends DomainException {
    public UserAlreadyInactiveException() {
        super("User already inactive", ErrorCode.USER_ALREADY_INACTIVE);
    }
}
