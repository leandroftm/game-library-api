package com.leandroftm.game_library_api.security.controller;

import com.leandroftm.game_library_api.domain.dto.request.CreateUserRequest;
import com.leandroftm.game_library_api.security.dto.AuthResponse;
import com.leandroftm.game_library_api.security.dto.LoginRequest;
import com.leandroftm.game_library_api.security.service.AuthService;
import com.leandroftm.game_library_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody CreateUserRequest request){
        userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
        String token = authService.authenticate(request);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
