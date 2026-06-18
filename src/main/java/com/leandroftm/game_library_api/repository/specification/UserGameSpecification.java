package com.leandroftm.game_library_api.repository.specification;

import com.leandroftm.game_library_api.domain.entity.UserGame;
import com.leandroftm.game_library_api.domain.enums.GameStatus;
import org.springframework.data.jpa.domain.Specification;

public class UserGameSpecification {

    public static Specification<UserGame> hasUserId(Long userId) {
        return (
                root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user").get("id"), userId
                );
    }

    public static Specification<UserGame> hasStatus(GameStatus status) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status
                );
    }

    public static Specification<UserGame> isFavorite(Boolean favorite) {
        return (
                root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("favorite"), favorite
                );
    }

    public static Specification<UserGame> containsGameName(String gameName) {
        return(
                root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("gameName")), "%" + gameName.toLowerCase() + "%"
                );
    }

}
