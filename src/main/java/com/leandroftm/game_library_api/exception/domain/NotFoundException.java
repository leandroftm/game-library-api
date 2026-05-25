package com.leandroftm.game_library_api.exception;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;

public class NotFoundException extends DomainException {
    public NotFoundException(String message,  ErrorCode errorCode) {
        super(message, errorCode);

    }
}
