package com.brasajava.routerfunctionstyle.exception;

public class PersonPatchNotFoundFieldException extends ServiceException {

  private static final long serialVersionUID = 1L;

  public PersonPatchNotFoundFieldException() {
    super("Lead Not Found");
  }

  public PersonPatchNotFoundFieldException(String message) {
    super(message);
  }
}
