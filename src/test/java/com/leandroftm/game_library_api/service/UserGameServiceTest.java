package com.leandroftm.game_library_api.service;

import com.leandroftm.game_library_api.domain.dto.request.AddGameRequest;
import com.leandroftm.game_library_api.domain.dto.request.UpdateGameStatusRequest;
import com.leandroftm.game_library_api.domain.dto.response.GameSearchResponse;
import com.leandroftm.game_library_api.domain.dto.response.UserGameResponse;
import com.leandroftm.game_library_api.domain.entity.User;
import com.leandroftm.game_library_api.domain.entity.UserGame;
import com.leandroftm.game_library_api.domain.enums.GameStatus;
import com.leandroftm.game_library_api.exception.domain.user.UserNotFoundException;
import com.leandroftm.game_library_api.exception.domain.user_game.GameAlreadyExistsException;
import com.leandroftm.game_library_api.exception.domain.user_game.InvalidGameStatusException;
import com.leandroftm.game_library_api.repository.UserGameRepository;
import com.leandroftm.game_library_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserGameServiceTest {

    @Mock
    private UserGameRepository userGameRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IgdbService igdbService;

    @InjectMocks
    private UserGameService userGameService;


    @Test
    void shouldAddGameSuccessfully() {
        GameSearchResponse response = new GameSearchResponse(
                1L,
                "Fallout",
                LocalDate.now(),
                List.of("PC"),
                List.of("Role Playing Game")
        );

        when(igdbService.searchGameById(1L)).thenReturn(response);
        when(userGameRepository.existsByUserIdAndIgdbId(1L, 1L)).thenReturn(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(
                new User(
                        "UserName",
                        "encoded-password",
                        "email@email.com"
                )));

        userGameService.addGame(1L, new AddGameRequest(1L));

        verify(igdbService).searchGameById(1L);
        verify(userGameRepository).existsByUserIdAndIgdbId(1L, 1L);
        verify(userRepository).findById(1L);
        verify(userGameRepository).save(argThat(game ->
                game.getIgdbId() == 1L &&
                        game.getGameName().equals("Fallout")
        ));
        verifyNoMoreInteractions(userGameRepository, userRepository, igdbService);
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldReturnGamesByUserSuccessfully() {
        Pageable pageable = PageRequest.of(0, 10);
        List<UserGame> games = List.of(new UserGame(1L, "Fallout"));
        Page<UserGame> response = new PageImpl<>(games, pageable, games.size());

        when(userGameRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(response);

        Page<UserGameResponse> result = userGameService.getGamesByUser(1L, null, null, null, pageable);

        assertNotNull(result);
        assertEquals(1L, result.getContent().getFirst().igdbId());
        assertEquals("Fallout", result.getContent().getFirst().gameName());

        verify(userGameRepository).findAll(any(Specification.class), any(Pageable.class));
        verifyNoMoreInteractions(userGameRepository);
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldReturnGamesByUserAndStatusSuccessfully() {
        Pageable pageable = PageRequest.of(0, 10);
        List<UserGame> games = List.of(new UserGame(1L, "Fallout"));
        ReflectionTestUtils.setField(games.getFirst(), "status", GameStatus.PLAYING);

        Page<UserGame> response = new PageImpl<>(games, pageable, games.size());

        when(userGameRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(response);

        Page<UserGameResponse> result = userGameService.getGamesByUser(1L, GameStatus.PLAYING, null, null, pageable);

        assertNotNull(result);
        assertEquals(1L, games.getFirst().getIgdbId());
        assertEquals("Fallout", games.getFirst().getGameName());
        assertEquals(GameStatus.PLAYING, games.getFirst().getStatus());

        verify(userGameRepository).findAll(any(Specification.class), any(Pageable.class));
        verifyNoMoreInteractions(userGameRepository);
    }

    @Test
    void shouldUpdateGameStatusSuccessfully() {
        UpdateGameStatusRequest request = new UpdateGameStatusRequest(GameStatus.PLAYING);
        UserGame savedGame = new UserGame(1L, "Fallout");
        ReflectionTestUtils.setField(savedGame, "status", GameStatus.TO_PLAY);

        when(userGameRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(savedGame));

        userGameService.updateGameStatus(1L, 1L, request);

        verify(userGameRepository).findByIdAndUserId(1L, 1L);
        verify(userGameRepository).save(argThat(game ->
                game.getStatus().equals(GameStatus.PLAYING)));
        verifyNoMoreInteractions(userGameRepository);
    }

    @Test
    void shouldFavoriteGameSuccessfully() {
        UserGame savedGame = new UserGame(1L, "Fallout");

        when(userGameRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(savedGame));

        userGameService.favoriteGame(1L, 1L);

        verify(userGameRepository).findByIdAndUserId(1L, 1L);
        verify(userGameRepository).save(argThat(UserGame::isFavorite));
        verifyNoMoreInteractions(userGameRepository);
    }

    @Test
    void shouldUnfavoriteGameSuccessfully() {
        UserGame savedGame = new UserGame(1L, "Fallout");

        when(userGameRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(savedGame));
        savedGame.favoriteGame();

        userGameService.unfavoriteGame(1L, 1L);

        verify(userGameRepository).findByIdAndUserId(1L, 1L);
        verify(userGameRepository).save(argThat(game ->
                !game.isFavorite()));
        verifyNoMoreInteractions(userGameRepository);
    }

    @Test
    void shouldDeleteGameSuccessfully() {
        UserGame savedGame = new UserGame(1L, "Fallout");
        when(userGameRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(savedGame));

        userGameService.deleteGame(1L, 1L);

        verify(userGameRepository).findByIdAndUserId(1L, 1L);
        verify(userGameRepository).delete(savedGame);
        verifyNoMoreInteractions(userGameRepository);
    }

    @Test
    void shouldReturnConflictWhenGameExistsByUserIdAndIgdbId() {
        GameSearchResponse response = new GameSearchResponse(
                1L,
                "Fallout",
                LocalDate.now(),
                List.of("PC"),
                List.of("Role Playing Game")
        );

        when(igdbService.searchGameById(1L)).thenReturn(response);
        when(userGameRepository.existsByUserIdAndIgdbId(1L, 1L)).thenReturn(true);

        assertThrows(GameAlreadyExistsException.class, () -> userGameService.addGame(1L, new AddGameRequest(1L)));
        verifyNoMoreInteractions(userGameRepository, igdbService);
        verifyNoInteractions(userRepository);
    }

    @Test
    void shouldReturnNotFoundWhenUserNotFound() {
        GameSearchResponse response = new GameSearchResponse(
                1L,
                "Fallout",
                LocalDate.now(),
                List.of("PC"),
                List.of("Role Playing Game")
        );

        when(igdbService.searchGameById(1L)).thenReturn(response);
        when(userGameRepository.existsByUserIdAndIgdbId(1L, 1L)).thenReturn(false);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userGameService.addGame(1L, new AddGameRequest(1L)));
        verifyNoMoreInteractions(userGameRepository, igdbService, userRepository);
    }

    @Test
    void shouldReturnBadRequestWhenGameStatusIsInvalid() {
        UserGame userGame = new UserGame(
                1L,
                "Fallout"
        );
        ReflectionTestUtils.setField(userGame, "status", GameStatus.TO_PLAY);
        when(userGameRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(userGame));

        assertThrows(InvalidGameStatusException.class, () -> userGameService.updateGameStatus(1L, 1L, new UpdateGameStatusRequest(GameStatus.TO_PLAY)));
       verifyNoMoreInteractions(userGameRepository);
    }
}
