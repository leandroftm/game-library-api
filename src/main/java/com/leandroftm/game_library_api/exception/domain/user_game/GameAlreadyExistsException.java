package com.leandroftm.game_library_api.exception.domain.user_game;

public class GameAlreadyExistsException extends RuntimeException {
  public GameAlreadyExistsException(String message) {
    super(message);
  }
}
