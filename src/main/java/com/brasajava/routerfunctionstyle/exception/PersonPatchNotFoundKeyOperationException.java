package com.brasajava.routerfunctionstyle.exception;

public class PersonPatchNotFoundKeyOperationException extends ServiceException {

  private static final long serialVersionUID = 1L;

  public PersonPatchNotFoundKeyOperationException() {
    super("THE PATCH OBJECT MISS SOME OPERATION KEY(s) LIKE: opt, path or value.");
  }

  public PersonPatchNotFoundKeyOperationException(String message) {
    super(message);
  }
}
