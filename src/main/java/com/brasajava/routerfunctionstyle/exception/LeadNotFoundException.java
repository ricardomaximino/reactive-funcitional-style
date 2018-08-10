package com.brasajava.routerfunctionstyle.exception;

public class LeadNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public LeadNotFoundException() {
    super("Lead Not Found");
  }

  public LeadNotFoundException(String message) {
    super(message);
  }
}
