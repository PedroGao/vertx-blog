package com.pedro.vertx.common.exception;

public class AuthorizationException extends RuntimeException {

  public AuthorizationException(String message) {
    super(message);
  }
}
