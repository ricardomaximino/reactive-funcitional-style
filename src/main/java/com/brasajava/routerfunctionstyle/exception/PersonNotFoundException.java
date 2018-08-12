package com.brasajava.routerfunctionstyle.exception;

public class PersonNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public PersonNotFoundException() {
    super("Lead Not Found");
  }

  public PersonNotFoundException(String message) {
    super(message);
  }
}
