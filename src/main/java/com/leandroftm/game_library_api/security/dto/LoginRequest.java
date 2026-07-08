package com.leandroftm.game_library_api.security.dto;

public record LoginRequest(
       String email,
       String password
) {
}
