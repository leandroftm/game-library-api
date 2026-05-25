package com.leandroftm.game_library_api.exception.domain.user;

public class UserAnreadyActiveException extends RuntimeException {
  public UserAnreadyActiveException(String message) {
    super(message);
  }
}
