package com.leandroftm.game_library_api.exception;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import lombok.Getter;

public class DomainException extends RuntimeException {
    @Getter
    private ErrorCode errorCode;
    public DomainException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
