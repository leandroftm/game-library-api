package com.leandroftm.game_library_api.exception.domain.user;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import com.leandroftm.game_library_api.exception.domain.DataIntegrityViolationException;

public class UserEmailAlreadyExistsException extends DataIntegrityViolationException {
    public UserEmailAlreadyExistsException() {
        super(
                "Email already exists", ErrorCode.USER_EMAIL_ALREADY_EXISTS
        );
    }
}
