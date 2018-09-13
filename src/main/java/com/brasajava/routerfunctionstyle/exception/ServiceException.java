package com.brasajava.routerfunctionstyle.exception;

public class ServiceException extends RuntimeException {

  private static final long serialVersionUID = -3802945117417697778L;

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(Throwable cause) {
    super(cause);
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
