package com.leandroftm.game_library_api.repository;

import com.leandroftm.game_library_api.domain.entity.UserGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGameRepository extends JpaRepository<UserGame, Long> {
}
