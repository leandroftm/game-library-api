package com.leandroftm.game_library_api.exception.domain.user;

public class PasswordConflictException extends RuntimeException {
  public PasswordConflictException(String message) {
    super(message);
  }
}
