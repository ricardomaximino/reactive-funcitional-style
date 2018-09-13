package com.brasajava.routerfunctionstyle.api.dto;

public class ErrorMessageDTO {

  private long timestamp;
  private int status;
  private String error;
  private String exception;
  private String message;

  public ErrorMessageDTO() {}

  public ErrorMessageDTO(
      long timestamp, int status, String error, String exception, String message) {
    this.timestamp = timestamp;
    this.status = status;
    this.error = error;
    this.exception = exception;
    this.message = message;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getException() {
    return exception;
  }

  public void setException(String exception) {
    this.exception = exception;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "ErrorMessageDTO [timestamp="
        + timestamp
        + ", status="
        + status
        + ", error="
        + error
        + ", exception="
        + exception
        + ", message="
        + message
        + "]";
  }
}
