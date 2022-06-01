package io.devynlab.eldotrans.generic.exception;

public class InvalidDataException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private String message = "Invalid data";

  public InvalidDataException() {
    super();
  }

  public InvalidDataException(String message) {
    super(message);
    this.message = message;
  }

  public InvalidDataException(Throwable cause) {
    super(cause);
  }

  public InvalidDataException(String message, Throwable cause) {
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
