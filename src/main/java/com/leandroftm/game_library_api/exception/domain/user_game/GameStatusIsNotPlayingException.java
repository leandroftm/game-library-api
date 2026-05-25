package com.leandroftm.game_library_api.exception.domain.user_game;

public class GameStatusIsNotPlayingException extends RuntimeException {
  public GameStatusIsNotPlayingException(String message) {
    super(message);
  }
}
