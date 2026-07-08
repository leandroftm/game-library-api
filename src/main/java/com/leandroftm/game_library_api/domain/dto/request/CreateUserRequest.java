package com.leandroftm.game_library_api.domain.dto.request;

import jakarta.validation.constraints.*;

public record CreateUserRequest(
        @NotBlank
        @Size(min = 5, max = 50)
        String userName,
        @NotBlank
        @Size(min = 6, max = 25)
        String password,
        @NotBlank
        @Email
        @Size(min = 6, max = 100)
        String email
) {
}
