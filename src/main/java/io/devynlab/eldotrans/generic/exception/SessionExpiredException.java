package io.devynlab.eldotrans.generic.exception;

public class SessionExpiredException extends Exception {

  private static final long serialVersionUID = 1L;

  private String message = "Session Expired";

  public SessionExpiredException() {
    super();
  }

  public SessionExpiredException(String message) {
    super(message);
    this.message = message;
  }

  public SessionExpiredException(Throwable cause) {
    super(cause);
  }

  public SessionExpiredException(String message, Throwable cause) {
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
