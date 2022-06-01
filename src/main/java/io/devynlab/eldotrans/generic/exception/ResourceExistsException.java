package io.devynlab.eldotrans.generic.exception;

public class ResourceExistsException extends RuntimeException {

  private static final long serialVersionUID = 5861310537366287163L;

  public ResourceExistsException() {
    super();
  }

  private String message = "Resource Already Exists";


  public ResourceExistsException(final String message, final Throwable cause) {
    super(message, cause);
    this.message = message;
  }

  public ResourceExistsException(String message) {
    super(message);
    this.message = message;
  }


  public ResourceExistsException(final Throwable cause) {
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
}
