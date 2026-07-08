package com.leandroftm.game_library_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leandroftm.game_library_api.domain.dto.request.AddGameRequest;
import com.leandroftm.game_library_api.domain.dto.request.UpdateGameStatusRequest;
import com.leandroftm.game_library_api.domain.dto.response.UserGameResponse;
import com.leandroftm.game_library_api.domain.entity.User;
import com.leandroftm.game_library_api.domain.enums.GameStatus;
import com.leandroftm.game_library_api.security.entity.UserPrincipal;
import com.leandroftm.game_library_api.service.UserGameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserGameService userGameService;


    @Test
    @WithMockUser
    void shouldReturnAllGamesUsingFiltersFromUserSuccessfully() throws Exception {
        User user = newUser();
        ReflectionTestUtils.setField(user, "id", 1L);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        Page<UserGameResponse> page = new PageImpl<>(List.of(
                newGameResponse()
        ));

        when(userGameService.getGamesByUser(any(Long.class), any(GameStatus.class), any(Boolean.class), any(String.class), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/users/me/games")
                        .with(user(userPrincipal))
                        .param("status", "PLAYING")
                        .param("favorite", "true")
                        .param("gameName", "Fallout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(page)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[*].igdbId").value(1))
                .andExpect(jsonPath("$.content[*].gameName").value("Fallout"))
                .andExpect(jsonPath("$.content[*].favorite").value(true))
                .andExpect(jsonPath("$.content[*].status").value("PLAYING"));
    }

    @Test
    @WithMockUser
    void shouldAddGameOnUserSuccessfully() throws Exception {
        User user = newUser();
        ReflectionTestUtils.setField(user, "id", 1L);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        userGameService.addGame(any(Long.class), any(AddGameRequest.class));

        mockMvc.perform(post("/users/me/games")
                        .with(user(userPrincipal))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void shouldModifyGameStatusSuccessfully() throws Exception {
        User user = newUser();
        ReflectionTestUtils.setField(user, "id", 1L);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        userGameService.updateGameStatus(any(Long.class), any(Long.class), any(UpdateGameStatusRequest.class));

        mockMvc.perform(patch("/users/me/games/{gameId}/status", 1L)
                        .with(user(userPrincipal))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void shouldFavoriteGameSuccessfully() throws Exception {
        User user = newUser();
        ReflectionTestUtils.setField(user, "id", 1L);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        userGameService.favoriteGame(any(Long.class), any(Long.class));

        mockMvc.perform(patch("/users/me/games/{gameId}/favorite", 1L)
                        .with(user(userPrincipal))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void shouldUnfavoriteGameSuccessfully() throws Exception {
        User user = newUser();
        ReflectionTestUtils.setField(user, "id", 1L);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        userGameService.unfavoriteGame(any(Long.class), any(Long.class));

        mockMvc.perform(patch("/users/me/games/{gameId}/unfavorite", 1L)
                        .with(user(userPrincipal))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void shouldDeleteGameSuccessfully() throws Exception {
        User user = newUser();
        ReflectionTestUtils.setField(user, "id", 1L);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        userGameService.deleteGame(any(Long.class), any(Long.class));

        mockMvc.perform(delete("/users/me/games/{gameId}", 1L)
                        .with(user(userPrincipal))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNoContent());
    }

    private User newUser() {
        return new User(
                "test user",
                "encoded-password-test",
                "test@test.com"
        );
    }

    private UserGameResponse newGameResponse() {
        return new UserGameResponse(
                1L,
                1L,
                "Fallout",
                true,
                LocalDateTime.now(),
                null,
                GameStatus.PLAYING
        );
    }
}
