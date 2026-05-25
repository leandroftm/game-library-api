package com.leandroftm.game_library_api.exception.domain.user_game;

public class GameStatusAlreadyDroppedException extends RuntimeException {
  public GameStatusAlreadyDroppedException(String message) {
    super(message);
  }
}
