package com.leandroftm.game_library_api.domain.dto.response;

import com.leandroftm.game_library_api.domain.entity.User;

import java.util.List;

public record UserResponse(
        Long id,
        String userName,
        String email,
        List<UserGameResponse> games
) {
    public UserResponse(User user) {
        this(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getGames()
                        .stream()
                        .map(UserGameResponse::new)
                        .toList()
        );
    }
}
