package com.leandroftm.game_library_api.domain.dto.response;

import com.leandroftm.game_library_api.domain.entity.User;
import com.leandroftm.game_library_api.domain.entity.UserGame;

import java.util.List;

public record UserResponse(
        String userName,
        String password,
        String email,
        List<UserGame> games
) {

    public UserResponse(User user){
        this(user.getUserName(),user.getPassword(),user.getEmail(),user.getGames());
    }
}
