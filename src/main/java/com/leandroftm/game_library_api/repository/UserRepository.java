package com.leandroftm.game_library_api.repository;

import com.leandroftm.game_library_api.domain.dto.response.UserResponse;
import com.leandroftm.game_library_api.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    Optional<UserResponse> findByEmail(String email);

    Optional<UserResponse>  findByUserName(String userName);

    boolean existsByUserNameAndIdNot(String userName, long id);

    boolean existsByEmailAndIdNot(String email, long id);
}
