package com.leandroftm.game_library_api.controller;

import com.leandroftm.game_library_api.domain.dto.request.AddGameRequest;
import com.leandroftm.game_library_api.domain.dto.request.UpdateGameStatusRequest;
import com.leandroftm.game_library_api.domain.dto.response.UserGameResponse;
import com.leandroftm.game_library_api.domain.dto.response.UserResponse;
import com.leandroftm.game_library_api.domain.enums.GameStatus;
import com.leandroftm.game_library_api.security.entity.UserPrincipal;
import com.leandroftm.game_library_api.service.UserGameService;
import com.leandroftm.game_library_api.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController{

    //private final UserService userService;

    private final UserGameService userGameService;

    //CREATE user moved to auth

    //Test purpose only
//    @GetMapping("/test/getallusers")
//    public ResponseEntity<Page<UserResponse>> getAllUsers(@PageableDefault(size = 20, sort = "userName") Pageable pageable) {
//        Page<UserResponse> page = userService.getUsers(pageable);
//
//        return ResponseEntity.ok(page);
//    }

    @GetMapping("/me/games")
    public ResponseEntity<Page<UserGameResponse>> getAllGamesFromUserByFilter(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestParam(required = false) GameStatus status,
            @RequestParam(required = false) Boolean favorite,
            @RequestParam(required = false) String gameName,
            @PageableDefault(size = 20, sort = "gameName") Pageable pageable) {

        Page<UserGameResponse> page = userGameService.getGamesByUser(user.getId(), status, favorite, gameName, pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping("/me/games")
    public ResponseEntity<Void> addGame(@AuthenticationPrincipal UserPrincipal user, @RequestBody AddGameRequest request) {
        userGameService.addGame(user.getId(), request);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/me/games/{gameId}/status")
    public ResponseEntity<Void> changeGameStatus(@AuthenticationPrincipal UserPrincipal user, @PathVariable Long gameId, @RequestBody UpdateGameStatusRequest request) {
        userGameService.updateGameStatus(user.getId(), gameId, request);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/me/games/{gameId}/favorite")
    public ResponseEntity<Void> favoriteGame(@AuthenticationPrincipal UserPrincipal user, @PathVariable Long gameId) {
        userGameService.favoriteGame(user.getId(), gameId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/me/games/{gameId}/unfavorite")
    public ResponseEntity<Void> unfavoriteGame(@AuthenticationPrincipal UserPrincipal user, @PathVariable Long gameId) {
        userGameService.unfavoriteGame(user.getId(), gameId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me/games/{gameId}")
    public ResponseEntity<Void> deleteGame(@AuthenticationPrincipal UserPrincipal user, @PathVariable Long gameId) {
        userGameService.deleteGame(user.getId(), gameId);
        return ResponseEntity.noContent().build();
    }
}
