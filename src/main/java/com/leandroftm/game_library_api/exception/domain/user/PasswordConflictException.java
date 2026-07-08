package com.leandroftm.game_library_api.exception.domain.user;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import com.leandroftm.game_library_api.exception.domain.DataIntegrityViolationException;

public class PasswordConflictException extends DataIntegrityViolationException {
    public PasswordConflictException() {
        super(
                "The saved password and the new password are the same", ErrorCode.USER_PASSWORD_CONFLICT
        );
    }
}
