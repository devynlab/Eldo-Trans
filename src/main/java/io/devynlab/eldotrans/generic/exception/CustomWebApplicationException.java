package io.devynlab.eldotrans.generic.exception;

import org.springframework.http.HttpStatus;

public class CustomWebApplicationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private String message = null;
  private HttpStatus status;

  public CustomWebApplicationException() {
    super();
  }

  public CustomWebApplicationException(String message, HttpStatus status) {
    this.message = message;
    this.status = status;
  }

  public CustomWebApplicationException(Throwable cause) {
    super(cause);
  }

  @Override
  public String toString() {
    return message;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }
}
