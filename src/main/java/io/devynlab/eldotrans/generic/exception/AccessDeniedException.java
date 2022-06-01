package io.devynlab.eldotrans.generic.exception;

public class AccessDeniedException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private String message = "Access Denied Error";

  public AccessDeniedException() {
    super();
  }

  public AccessDeniedException(String message) {
    super(message);
    this.message = message;
  }

  public AccessDeniedException(Throwable cause) {
    super(cause);
  }

  public AccessDeniedException(String message, Throwable cause) {
    super(message, cause);
  }

  @Override
  public String toString() {
    return message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
