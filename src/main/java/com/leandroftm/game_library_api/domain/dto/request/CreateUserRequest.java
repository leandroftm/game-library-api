package com.leandroftm.game_library_api.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank
        @Min(5)
        @Max(25)
        String userName,
        @NotBlank
        @Min(6)
        @Max(25)
        String password,
        @NotBlank
        @Email
        @Max(50)
        String email
) {
}
