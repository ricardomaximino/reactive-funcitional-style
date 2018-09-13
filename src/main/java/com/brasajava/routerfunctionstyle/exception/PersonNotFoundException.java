package com.brasajava.routerfunctionstyle.exception;

public class PersonNotFoundException extends ServiceException {

  private static final long serialVersionUID = 1L;

  public PersonNotFoundException() {
    super("Person Not Found");
  }

  public PersonNotFoundException(String message) {
    super(message);
  }
}
