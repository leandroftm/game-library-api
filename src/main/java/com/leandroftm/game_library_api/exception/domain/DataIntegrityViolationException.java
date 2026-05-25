package com.leandroftm.game_library_api.exception.domain;

public class DataIntegrityViolationException extends RuntimeException {
  public DataIntegrityViolationException(String message) {
    super(message);
  }
}
