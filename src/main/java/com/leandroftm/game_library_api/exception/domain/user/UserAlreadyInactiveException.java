package com.leandroftm.game_library_api.exception.domain.user;

public class UserAlreadyInactiveException extends RuntimeException {
  public UserAlreadyInactiveException(String message) {
    super(message);
  }
}
