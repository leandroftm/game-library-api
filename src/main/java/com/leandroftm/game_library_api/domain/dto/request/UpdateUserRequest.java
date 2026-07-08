package com.leandroftm.game_library_api.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @Size(min = 5, max = 50)
        String userName,
        @Size(min = 6, max = 25)
        String password,
        @Email
        @Size(min = 6, max = 100)
        String email
) {
}
