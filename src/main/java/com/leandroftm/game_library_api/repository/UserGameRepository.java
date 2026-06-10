package com.leandroftm.game_library_api.repository;

import com.leandroftm.game_library_api.domain.entity.UserGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserGameRepository extends JpaRepository<UserGame, Long> {
    boolean existsByUserIdAndIgdbId(Long userId, Long igdbGameId);

    Optional<UserGame> findByIdAndUserId(Long gameId, Long userId);
}
