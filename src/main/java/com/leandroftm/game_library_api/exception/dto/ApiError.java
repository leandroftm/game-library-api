package com.leandroftm.game_library_api.exception.dto;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
        int status,
        String error,
        ErrorCode errorCode,
        List<String> messages,
        String path,
        LocalDateTime timestamp
) {
}
