package com.leandroftm.game_library_api.repository;

import com.leandroftm.game_library_api.domain.entity.UserGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserGameRepository extends JpaRepository<UserGame, Long>, JpaSpecificationExecutor<UserGame> {
    boolean existsByUserIdAndIgdbId(Long userId, Long igdbGameId);

    Optional<UserGame> findByIdAndUserId(Long gameId, Long userId);

}
