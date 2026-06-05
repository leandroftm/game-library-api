package com.leandroftm.game_library_api.exception.domain.user;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import com.leandroftm.game_library_api.exception.domain.DataIntegrityViolationException;

public class UserNameAlreadyExistsException extends DataIntegrityViolationException {
    public UserNameAlreadyExistsException() {
        super(
                "User name already exists", ErrorCode.USER_NAME_ALREADY_EXISTS
        );
    }
}
