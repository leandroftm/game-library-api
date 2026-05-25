package com.leandroftm.game_library_api.exception.domain;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import lombok.Getter;

public class DataIntegrityViolationException extends RuntimeException {
    @Getter
    private ErrorCode errorCode;

    public DataIntegrityViolationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
