package com.leandroftm.game_library_api.controller;

import com.leandroftm.game_library_api.domain.dto.request.AddGameRequest;
import com.leandroftm.game_library_api.domain.dto.request.CreateUserRequest;
import com.leandroftm.game_library_api.domain.dto.response.UserResponse;
import com.leandroftm.game_library_api.service.UserGameService;
import com.leandroftm.game_library_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserGameService userGameService;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(@PageableDefault(size = 20, sort = "userName") Pageable pageable){
        Page<UserResponse> page =  userService.getUsers(pageable);

        return ResponseEntity.ok(page);
    }

    @PostMapping("/{userId}/games")
    public ResponseEntity<Void> addGame(@PathVariable Long userId, @RequestBody AddGameRequest request) {
        userGameService.addGame(userId, request);

        return ResponseEntity.noContent().build();
    }
}
