package com.leandroftm.game_library_api.repository;

import com.leandroftm.game_library_api.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
