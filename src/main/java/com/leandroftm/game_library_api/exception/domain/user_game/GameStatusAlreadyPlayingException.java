package com.leandroftm.game_library_api.exception.domain.user_game;

public class GameStatusAlreadyPlayingException extends RuntimeException {
  public GameStatusAlreadyPlayingException(String message) {
    super(message);
  }
}
