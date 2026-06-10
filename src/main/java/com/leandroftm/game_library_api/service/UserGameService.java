package com.leandroftm.game_library_api.service;

import com.leandroftm.game_library_api.domain.dto.request.AddGameRequest;
import com.leandroftm.game_library_api.domain.dto.response.GameSearchResponse;
import com.leandroftm.game_library_api.domain.entity.User;
import com.leandroftm.game_library_api.domain.entity.UserGame;
import com.leandroftm.game_library_api.exception.domain.user.UserNotFoundException;
import com.leandroftm.game_library_api.exception.domain.user_game.GameAlreadyExistsException;
import com.leandroftm.game_library_api.repository.UserGameRepository;
import com.leandroftm.game_library_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserGameService {

    private final IgdbService igdbService;

    private final UserGameRepository userGameRepository;

    private final UserRepository userRepository;

    public void addGame(long userId, AddGameRequest request){
        GameSearchResponse igdbResponse = igdbService.searchGameById(request.igdbGameId());
        UserGame userGame = new UserGame(
                igdbResponse.id(),
                igdbResponse.name()
        );

        if(userGameRepository.existsByUserIdAndIgdbId(userId, userGame.getId())){
            throw new GameAlreadyExistsException();
        }

        User user =  userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        user.addGame(userGame);
        userGameRepository.save(userGame);
    }
}
